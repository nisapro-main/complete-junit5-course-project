# complete-junit5-course-project
					---------------------------------------------
							Junit 5 PROJECT DESCRIPTION
					---------------------------------------------

--------------------------------
 TITLE
-------------------------------- 
Online flight reservation system (server side)

--------------------------------
 OBJECTIVES
--------------------------------
To acquire practical experience with Junit 5 by applying the fundamental concepts and techniques taught in the course.
By the end of this project you'll be equiped with the skills and experience to tackle with confidence the use of Junit 5 on any real-world Software system using the awesome Junit 5 framework!
 
--------------------------------
 DESCRIPTION
-------------------------------- 
The project consists of writing state based tests against a simplified server side sub-system of an online flight reservation system.
The main class of this sub-system is 'ReservationManager.Java'.

To make the project more realistic, this main class is deliberately poorly designed. As such, it is highly recommended that you use the powerful refactoring features of your preferred IDE (we recommend using IJ - pleasee see the IJ course) to improve the design and implementation of the sub-system as you write your tests.
Let your Junit tests guide the design of your system. A simple way to do this is to reason about, desgin and implement the tests for your system and then refactor the production code.

You should strive for nearly 100% test coverage due to the small size of this project.

Note: It's recommended that you create a github account if you don't have one so that you can fork this project. 
However this is optional: you can just download the entire project sources using the "zip" (for Windows) or "dmg" (for Mac).

-------------------------------- 
 COMPLETION STEPS
-------------------------------- 
1) Import this gradle project
2) Given that this is a realistic scenario it is recommended that you refactor the main class (ReservationManager) to improve the overall design
(DAO.java and Codec.java classes are provided to simplify your refactoring)
3) Write tests for at least the following scenarios (the more the better):

	3.1) Boundary conditions
	
	3.2) search flights use case with any permutation of the input parameters
	
	3.3) book use case with any permutation of the input parameters
	
	3.4) Loading of the CSV with different contents (empty, with return flights, with one-way flights only, etc)
	
	
Bonus points:
1) Create a github account so that you can share your project with others!
2) Refactor the class so that it's easier to test
