package com.craftofprogramming;

import com.craftofprogramming.ReservationManager.CityPairKey;
import com.craftofprogramming.ReservationManager.ClassSeat;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Nilton Santos 7/15/2019
 */
public class Main {

    private static final Map<LocalDate, Map<CityPairKey, Map<String,
            List<ClassSeat>>>> data = new HashMap<>();

    public static void main(String[] args) {
        try (BufferedReader reader = Files.newBufferedReader(Paths.get("src/main/resources/Flights.csv"))) {
            String line;
            boolean isFirstLine = true;
            while ((line = reader.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    continue;
                }
                final String[] tokens = line.split(",");
                int idx = 0;
                final LocalDate date = LocalDate.parse(getToken(tokens, idx++));
                // Date to CityPairKey to ClassType to (ascending price ordered) List of ClassSeat cityPairKeyMapMap!
                Map<CityPairKey, Map<String, List<ClassSeat>>> cityPairKeyMapMap = data.computeIfAbsent(date, k -> new HashMap<>());
                CityPairKey cityPairKey = CityPairKey.valueOf(getToken(tokens, idx++), getToken(tokens, idx++));
                Map<String, List<ClassSeat>> stringListMap = cityPairKeyMapMap.computeIfAbsent(cityPairKey, k -> new HashMap<>());
                String classType = getToken(tokens, idx++);
                List<ClassSeat> classSeats = stringListMap.computeIfAbsent(classType, k -> new ArrayList<>());
                String airline = getToken(tokens, idx++);
                int seats = Integer.valueOf(getToken(tokens, idx++));
                classSeats.add(ClassSeat.valueOf(airline, seats));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println(data);
    }

    static String getToken(String[] tokens, int idx) {
        final String token = tokens[idx];
        if (token == null) {
            throw new IllegalArgumentException(String.format("Found invalid token at index i:%d", idx));
        }
        return token.trim();
    }
}
