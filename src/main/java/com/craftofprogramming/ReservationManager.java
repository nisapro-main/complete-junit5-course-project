package com.craftofprogramming;

import sun.util.resources.ext.CurrencyNames_it_IT;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.Format;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.*;

/**
 * 1) Add Javadoc
 * 2)
 *
 *
 * @author Nilton Santos 7/14/2019
 */
public class ReservationManager {

    // Date to CityPairKey to ClassType to (ascending price ordered) List of ClassSeat map!
    private final Map<LocalDate, Map<CityPairKey, Map<String, List<ClassSeat>>>> data = new HashMap<>();
    private final Map<Long, Quote> quoteIdToQuotes = new HashMap<>();
    private final Map<Long, Reservation> reservationIdToReservations = new HashMap<>();

    public Quote searchFlights(String fromCity, String toCity, String fromDate, String toDate, String tripType,
                        String classType) {
        // make sure all input parameters are non-null and non-empty
        requireNonNullAndNonEmpty(fromCity);
        requireNonNullAndNonEmpty(fromDate);
        requireNonNullAndNonEmpty(tripType);
        requireNonNullAndNonEmpty(classType);
        if (tripType.equalsIgnoreCase("roundTrip")) {
            requireNonNullAndNonEmpty(toCity);
            requireNonNullAndNonEmpty(toDate);
        }

        final Quote resp = new Quote(fromCity, toCity, fromDate, toDate, tripType, classType);

        Map<CityPairKey, Map<String, List<ClassSeat>>> cityToClassTypeMap = data.get(LocalDate.parse(fromDate));

        if (cityToClassTypeMap == null) {
            resp.text = String.format("No seats available for the outbound flight on the date(s): %s",
                    fromDate);
            return resp;
        }

        Map<String, List<ClassSeat>> classTypeToSeatMap = cityToClassTypeMap.get(CityPairKey.valueOf(fromCity,toCity));

        if (classTypeToSeatMap == null) {
            resp.text = String.format("No seats available for the outbound flight for the city: %s",
                    fromCity);
            return resp;
        }

        List<ClassSeat> seatList = classTypeToSeatMap.get(classType);

        if (seatList == null) {
            resp.text = String.format("No seats available for the outbound flight on the classType: %s",
                    classType);
            return resp;
        }

        boolean found = false;
        for (final ClassSeat seat : seatList) {
            if (seat.availableCount > 0) {
                resp.price = seat.price;
                resp.inboundAirlineName = seat.airlineName;
                resp.inboundSeatCount = seat.availableCount;
                resp.inboundDepartureTime = seat.departureTime;

                resp.text = "Found seats for the provided requested parameters";

                found = true;
            }
        }

        if (!found || "OneWay".equalsIgnoreCase(tripType)) {
            return resp;
        }

        cityToClassTypeMap = data.get(LocalDate.parse(toDate));

        if (cityToClassTypeMap == null) {
            resp.text = String.format("No seats available for the return flight on the date(s): %s", toDate);
            return resp;
        }

        classTypeToSeatMap = cityToClassTypeMap.get(toCity);

        if (classTypeToSeatMap == null) {
            resp.text = String.format("No seats available for the return flight from the city: %s", toCity);
            return resp;
        }

        seatList = classTypeToSeatMap.get(classType);

        if (seatList == null) {
            resp.text = String.format("No seats available for the return flight on the classType: %s", classType);
            return resp;
        }

        for (final ClassSeat seat : seatList) {
            if (seat.availableCount > 0) {
                resp.price = resp.price.add(seat.price);
                resp.outboundAirlineName = seat.airlineName;
                resp.outboundSeatCount = seat.availableCount;
                resp.outboundDepartureTime = seat.departureTime;

                resp.text = "Found seats for the provided requested parameters";

                return resp;
            }
        }

        return resp;
    }

    public Reservation book(Long quoteId, List<Passenger> passengers) {
        requireNonNullAndNonEmpty(quoteId);
        requireNonNullAndNonEmpty(passengers);
        if (passengers.isEmpty()) {
            throw new RuntimeException(String.format("Supplied passengers list is empty for quoteId '%d'", quoteId));
        }
        Quote quote = this.quoteIdToQuotes.get(quoteId);
        if (quote == null) {
            throw new RuntimeException(String.format("Unable to find quote with id:%d", quoteId));
        }
        final Reservation reservation = new Reservation(quote, passengers);
        this.reservationIdToReservations.put(reservation.id, reservation);
        return reservation;
    }

    private static void requireNonNullAndNonEmpty(Object o) {
        Objects.requireNonNull(o);
        if (o instanceof String) {
            String s = (String) o;
            if (s.isEmpty()) {
                throw new RuntimeException("Can't be empty");
            }
        }
    }

    static class CityPairKey {
        final String from;
        final String to;

        private CityPairKey(String from, String to) {
            this.from = from;
            this.to = to;
        }

        static CityPairKey valueOf(String from, String to) {
            return new CityPairKey(from, to);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            final CityPairKey that = (CityPairKey) o;
            return from.equals(that.from) &&
                    to.equals(that.to);
        }

        @Override
        public int hashCode() {
            return Objects.hash(from, to);
        }

        @Override
        public String toString() {
            return String.format("%s->%s", from, to);
        }
    }

    private static class Passenger {
        String name;
        int age;
    }

    private static class Reservation {
        static long idGenerator =1;
        long id;
        Quote quote;
        List<Passenger> passengers;
        BigDecimal totalPrice;

        Reservation(Quote quote, List<Passenger> passengers) {
            this.id = ++idGenerator + quote.id;
            this.passengers = new ArrayList<>(passengers);
            this.quote = quote;
            this.totalPrice = quote.price.multiply(BigDecimal.valueOf(passengers.size()));
        }
    }

    static class Quote {
        static int idGenerator = 1;

        // request fields
        String fromCity;
        String toCity;
        String fromDate;
        String toDate;
        String tripType;
        String classType;

        // response fields
        long id;
        String text;
        BigDecimal price;
        int inboundSeatCount;
        String inboundAirlineName;
        String inboundDepartureTime;
        int outboundSeatCount;
        String outboundAirlineName;
        String outboundDepartureTime;

        Quote(String fromCity, String toCity, String fromDate, String toDate, String tripType,
              String classType) {
            this.fromCity = fromCity;
            this.toCity = toCity;
            this.fromDate = fromDate;
            this.toDate = toDate;
            this.tripType = tripType;
            this.classType = classType;

            this.text = "Couldn't find any seats for the provided request parameters";
            this.id = ++idGenerator;
        }
    }

    // one for each: economy, business, first
    static class ClassSeat {
        String airlineName;
        int availableCount;
        String departureTime;
        BigDecimal price;
        Random random = new Random();

        ClassSeat(String airline, int seats) {
            this.airlineName = airline;
            this.availableCount = seats;
            this.departureTime = LocalDateTime.now().format(DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT));
            this.price = BigDecimal.valueOf(random.nextDouble() * 1000).setScale(0, RoundingMode.HALF_EVEN);
        }

        static ClassSeat valueOf(String airline, int seats) {
            return new ClassSeat(airline, seats);
        }

        @Override
        public String toString() {
            return String.format("Airline:%s| Seats:%d | Departure-time=%s | Price-per-seat:%s", airlineName,
                    availableCount, departureTime, price);
        }
    }
}
