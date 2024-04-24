package kidsLMS;

public class Main {

	public static void main(String[] args) 
	{
		Server x = new Server();
		script1();
		assignments();
		x.printAllStudentAssignmentGradesinCourse(1, 1, 1);
		x.closeConnection();
		//displayInformation();
		
		//script1();
		//assignments();
	}
	
	public static void displayInformation()
	{
		Server x = new Server();
		System.out.println("Teachers:");
		
		x.printAllInstructors();
		
		System.out.println("\nStudents:");
		x.printAllStudents();
		
		System.out.println("\nCourses:");
		x.printAllCourses();
		
		x.closeConnection();
	}
	
	
	//demonstrate teacher creating a class and adding students
	public static void script1()
	{
		Server x = new Server();
		x.createCourse(1, "6th Grade Math");
		x.addStudentToCourse(1, 1, 1);
		x.addStudentToCourse(2, 1, 1);
		x.printAllStudentsFromCourse(1);
		
		x.removeStudentFromCourse(2, 1, 1);
		System.out.println("\n");
		x.printAllStudentsFromCourse(1);
		x.closeConnection();
	}
	
	public static void assignments()
	{
		Server x = new Server();
		
		System.out.println("Before Assignments are Graded");
		x.createAssignment(1, 1, "CH 1 Homework", "Paper Homework Assigned on 4/25");
		x.printStudentsOverallGradesinCourse(1, 1);
		
		System.out.println("\nAfter Assignments are Graded");
		x.gradeAssignment(1, 1, 1, 50);
		x.printStudentsOverallGradesinCourse(1, 1);
		x.closeConnection();
	}
	
	
}
