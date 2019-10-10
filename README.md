# complete-junit5-course-project
					---------------------------------------------
							Junit 5 PROJECT DESCRIPTION
					---------------------------------------------

--------------------------------
 TITLE
-------------------------------- 
Online flight reservation system (server side)

--------------------------------
 GOAL
--------------------------------
To acquire practical experience with Junit 5 by applying the fundamental concepts and techniques taught in the course.
By the end of this project you'll be uquiped with the skills and experience to tackle with confidence the use of Junit 5 on any real-world Software system using the awesome Junit 5 framework!
 
--------------------------------
 DESCRIPTION
-------------------------------- 
The project consists of writing interaction based tests against a simplified server side sub-system of an online flight reservation system.
The main class of this sub-system is 'OnlineReservationSystem.Java'.

To make the project more realistic, this main class is deliberately poorly designed. As such, it is highly recommended that you use the powerful refactoring features of your preferred IDE (we recommend using IJ - pleasee see the IJ course) to improve the design and implementation of the sub-system as you write your tests.

You should strive for nearly 100% test coverage due to the small size of this project.

The following sources are provided:


You can checkout the code using the url:

Note: It's recommended that you create a github account if you don't have one. However this is optional: you can just download the entire project sources using the "zip" (for Windows) or "dmg" (for Mac)

-------------------------------- 
 COMPLETION STEPS
-------------------------------- 
1) Create the IJ project using the provided sources (see IJ course lesson X)
2) Given that this is a realistic scenario it is recommended that you refactor the main class to improve the overall design
(DAO.java and Codec.java classes are provided to simplify your refactoring)
3) Write tests for at least the following scenarios (the more the better):

	3.1) Boundary conditions
	
	3.2) search flights use case with any permutation of the input parameters
	
	3.3) book use case with any permutation of the input parameters
	
	3.4) Loading of the CSV with different contents (empty, with return flights, with one-way flights only, etc)
	
	
Bonus points:
1) Create a github account so that you can share your project with others (see IJ course lesson X)
2) Refactor the class so that it's easier to test
