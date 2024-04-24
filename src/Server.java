package kidsLMS;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Server {
	/* 														!!!!VERY IMPORTANT!!!!!
	 * 								When an instance of this object is called, you must call closeConnection()
	 * 								when you are done using this class. This cleans up the database connection 
	 * 								
	 * 								EG: 	Server x = new Server();
	 * 										Code...
	 * 										x.closeConnection()
	 * */
	private List<Instructor> allInstructors = new ArrayList<Instructor>();
	private List<Student> allStudents = new ArrayList<Student>();
	private List<Course> allCourses = new ArrayList<Course>();
	private List<Assignment> allAssignments = new ArrayList<Assignment>();

	
	Connection con;
	
	//Constructor that gets all information from the database
	public Server()
	{
		//establish connection to database
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/kidsLMS","root", "");
			con = conn;
		} 
		catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println(e);
		}
		retrieveDatabase(); 
	}
	
	
	public void closeConnection()
	{
		try {
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	//print all classes this user is a member of
	public List<Course> getAllCoursesFromStudent(int studentId)
		{
			//List to be outputted
			List<Course> outputList = new ArrayList<Course>();
			
			//search algorithm
			for(Course course: allCourses)
			{
				for(User user: course.membersOfCourse)
				{
					if(user.getID() == studentId)
					{
						outputList.add(course);
					}
				}
			}
			
			return outputList;
		}
	
	/*Below are a list of functions that make INSERT/DELETE changes to the database
	 * 
	 * 
	 * */
	
	//creates a course verifies that a teacher is calling this method
	public void createCourse(int teacherId, String courseName)
	{
		for(Instructor x: allInstructors)
		{
			if(teacherId == x.getID())
			{
				
				try{  
					
					String query ="insert into Course(Id, Name, instructorID) Values(null, ?, ?)";  
					
					PreparedStatement st = con.prepareStatement(query);
					st.setString(1, courseName);
					st.setInt(2, teacherId);
					st.executeUpdate();
				    st.close();
					
					
					   
					}
					
					catch(Exception e)
						{ System.out.println(e);} 
			}
		}
		
		retrieveDatabase();
		
	}
	
	//deletes a course verifies that a teacher assigned to this course is calling this method
	public void deleteCourse(int courseId)
	{
		for(Course x: allCourses)
		{
			if(courseId == x.getId())
			{
				try{  
					String query ="delete from course where Id = ?";  
					PreparedStatement st = con.prepareStatement(query);
					st.setInt(1, x.getId());
					st.executeUpdate();
				    st.close();
				    
				    
				    
				    String query2 = "delete from studentsInCourse where courseID = ?";
				    PreparedStatement s = con.prepareStatement(query2);
				    s.setInt(1, courseId);
				    s.executeUpdate();
				    s.close();
				    
				    String query3 = "delete from assignments where courseID = ?";
				    PreparedStatement t = con.prepareStatement(query3);
				    t.setInt(1, courseId);
				    t.executeUpdate();
				    t.close();
				    
				    
				    
					}
					
					catch(Exception e)
						{ System.out.println(e);} 
			}
		}
		
		retrieveDatabase();
	}
	
	//teacher of a course can add a student to a course
	public void addStudentToCourse(int studentId, int courseId, int teacherId)
	{	
		boolean studentAlreadyInCourse = false;
		boolean validTeacher = false;
		
		
		//Validation
		for(Course x: allCourses)
		{
			//get the appropriate course
			if(x.getId() == courseId)
			{
				//Determine if teacher is valid
				if(teacherId == x.getInstructorID())
				{
					validTeacher = true;
				}
				
				//determine if student is already a member of the course
				for(Student y: x.membersOfCourse)
				{
					if(studentId == y.getID())
					{
						studentAlreadyInCourse = true;
					}
				}
			}

		}
		
		
		
		//Database Updating
		if(studentAlreadyInCourse == false & validTeacher == true)
		{
		
		try{  
			
			String query ="insert into studentsinCourse(StudentId, CourseID) Values(?, ?)";  
			
			PreparedStatement st = con.prepareStatement(query);
			st.setInt(1, studentId);
			st.setInt(2, courseId);
			st.executeUpdate();
		    st.close();
			
			
			   
			}
			
			catch(Exception e)
				{ System.out.println(e);} 
		
		retrieveDatabase();
		}
	}
	
	//teacher of a course can remove a student from a course
	public void removeStudentFromCourse(int studentId, int courseId, int teacherId)
	{
		boolean studentAlreadyInCourse = false;
		boolean validTeacher = false;
		
		
		//Validation
		for(Course x: allCourses)
		{
			//get the appropriate course
			if(x.getId() == courseId)
			{
				//Determine if teacher is valid
				if(teacherId == x.getInstructorID())
				{
					validTeacher = true;
				}
				
				//determine if student is already a member of the course
				for(Student y: x.membersOfCourse)
				{
					if(studentId == y.getID())
					{
						studentAlreadyInCourse = true;
					}
				}
			}
			
			

		}
		
		
		
		//Database Updating
		if(studentAlreadyInCourse == true & validTeacher == true)
		{
		
		try{  
			
			String query ="delete from studentsInCourse where studentID = ? AND courseID = ?";  
			
			PreparedStatement st = con.prepareStatement(query);
			st.setInt(1, studentId);
			st.setInt(2, courseId);
			st.executeUpdate();
		    st.close();
			
			
		    
			   
			}
			
			catch(Exception e)
				{ System.out.println(e);} 
		
		retrieveDatabase();
		}
	}
	
	
	public void createAssignment(int courseId, int teacherId, String title, String Desc)
	{
		for(Course x: allCourses)
		{
			//get the appropriate course
			if(x.getId() == courseId)
			{
				//Determine if teacher is valid
				if(teacherId == x.getInstructorID())
				{
					//create assignment for each student of the class
					for(Student y: x.membersOfCourse)
					{
						try{  
							
							String query ="insert into Assignments(Title, Descr, completed, grade, studentId, courseId) "
									+ "Values(?, ?, false, 0.0, ?, ?)";  
							
							PreparedStatement st = con.prepareStatement(query);
							st.setString(1, title );
							st.setString(2, Desc);
							st.setInt(3, y.getID());
							st.setInt(4, courseId);
							st.executeUpdate();
						    st.close();
							
							
							   
							}
							
							catch(Exception e)
								{ System.out.println(e);} 
					}
				}
			}
		}
		
		retrieveDatabase();
		
		
		
	}

	public void gradeAssignment(int courseId, int teacherId, int studentId, double grade)
	{
		for(Course x: allCourses)
		{
			//get the appropriate course
			if(x.getId() == courseId)
			{
				//Determine if teacher is valid
				if(teacherId == x.getInstructorID())
				{
					for(Assignment z: allAssignments)
					{
						if(z.getCourseId() == courseId && z.getStudentId() == studentId)
						{
							try{  
								
								String query ="update assignments set grade = ? where studentID = ? and courseID =?";  
								
								PreparedStatement st = con.prepareStatement(query);
								st.setDouble(1, grade);
								st.setInt(2, studentId);
								st.setInt(3, courseId);
								st.executeUpdate();
							    st.close();
								
								
								   
								}
								
								catch(Exception e)
									{ System.out.println(e);} 
							
							
						}
					}
				}
			}
		}
		retrieveDatabase();
	}

	public void completeAssignment(int courseId, int studentId)
	{
		
	}
	
	
	/*
	 * Below this line are print commands to print the contents of each list
	 ****************************************************************************************************************************
	 *
	 *printAllUsers() - prints out a list of all users with their ID's
	 *
	 *printAllInstructors() - prints out a list of all instructors with their ID's
	 *
	 *printAllStudents() - prints out a list of all Students with their ID's
	 *
	 *printAll Courses() - prints out a list of all courses with their ID's
	 * 
	 *printAllStudentsFromCourse() - prints out a list of all students enrolled in a specific course
	 *
	 *printAllCoursesFromStudent() - prints all courses a student is enrolled in
	 * 
	 * */
	
	
	public void printAllUsers()
	{
		for(Student x: allStudents)
		{
			System.out.println(x.getName() + ": " + x.getID());
		}
		for(Instructor x: allInstructors)
		{
			System.out.println(x.getName() + ": " + x.getID());
		}
	}
	
	public void printAllInstructors()
	{
		for(Instructor x: allInstructors)
		{
			System.out.println( x.getID()+ ": " + x.getName());
		}
	}
	
	public void printAllStudents()
	{
		for(Student x: allStudents)
		{
			System.out.println( x.getID()+ ": " + x.getName());
		}
	}
	
	public void printAllCourses()
	{
		for(Course x: allCourses)
		{
			System.out.println(x.getId() + ": " + x.getCourseName());
		}
	}
	
	public void printAllStudentsFromCourse(int courseId)
	{
		for(Course x: allCourses)
		{
			if(x.getId() == courseId)
			{
				System.out.println("Students in " + x.getCourseName() + "");
				for(Student y: x.membersOfCourse)
				{
					System.out.println(y.getName() + ": " + y.getID());
				}
			}
		}
	}
	
	public void printAllAssignments()
	{
		for(Assignment a: allAssignments)
		{
			System.out.println(a.getTitle() + " " + a.getStudentId());
		}
	}
	
	public void printAllCoursesFromStudent(int studentId)
	{
		
		//get name of student from ID to print description message
		for(Student a: allStudents)
		{
			if(a.getID() == studentId)
			{
				System.out.println("Courses " + a.getName() + " is enrolled in");
			}
		}
		
		
		
		//search algorithm for courses student is enrolled in
		for(Course course: allCourses)
		{
			for(User user: course.membersOfCourse)
			{
				if(user.getID() == studentId)
				{
					
					System.out.println(course.getCourseName());
				}
			}
		}	
	}
	
	public void printStudentsOverallGradesinCourse(int courseId, int teacherId)
	{
		for(Course x: allCourses)
		{
			if(x.getId() == courseId && x.getInstructorID() == teacherId)
			{
				for(Student y: x.membersOfCourse)
				{
					double gradesAccum = 0;
					double numberOfGrades = 0;
					for(Assignment z: allAssignments)
					{
						if(z.getCourseId() == courseId && z.getStudentId() == y.getID())
						{
							gradesAccum = gradesAccum + z.getGrade();
							numberOfGrades++;
						}
					}
					if(numberOfGrades == 0)
					{
						System.out.println("100%");
					}
					else
					{
						System.out.println(y.getName() + ": " + gradesAccum/numberOfGrades + "%");
					}
				}
			}
		}
	}
	
	public void printAllStudentAssignmentGradesinCourse(int studentId, int teacherId, int courseId)
	{
		boolean validTeacher = false;
		boolean validStudent = false;
		String studentName = null;
		
		//verify teacher ID and student is in the course
		for(Course x: allCourses)
		{
			//verify instructor
			if(x.getInstructorID() == teacherId)
			{
				validTeacher = true;
			}
			
			//verify student is in the course
			
			for(Student y: allStudents)
			{
				if(y.getID() == studentId)
				{
					validStudent = true;
					studentName = y.getName();
				}
					
			}
			
		}
		
		if(validTeacher == true && validStudent == true)
		{
			System.out.println(studentName + "'s Grades: ");
			for(Assignment a: allAssignments)
			{
				if(a.getStudentId() == studentId && a.getCourseId() == courseId)
				{
					System.out.println(a.getTitle() + ": " + a.getGrade() +"%");
				}
			}
		}
	}
	
	/*
	 * Below is all of the code to retrieve everything from the database
	 * 
	 * retrieveDatabase() - clears everything in the lists that store all of the information and calls functions
	 * 						to get everything out of the database
	 * 
	 * retrieveCourses() - retrieves all Course data from the database including which students are in which classes
	 * 
	 * retrieveInstructors() - retrieves all Instructor data from the database
	 * 
	 * retrieveStudents(0 - retrieves all Student data from the database
	 * 
	 * 
	 * 
	 * 
	 */

	
	public void retrieveDatabase()
	{
		allInstructors.clear();
		allStudents.clear();
		allCourses.clear();
		allAssignments.clear();
		
		retrieveAssignemnts();
		retrieveStudents();
		retrieveInstructors();
		retrieveCourses();
	}

	public void retrieveAssignemnts()
	{
		try{  
			
			//here sonoo is database name, root is username and password 
			Statement stmt=con.createStatement();  
			ResultSet rs=stmt.executeQuery("select * from Assignments");  
			while(rs.next())
			{
				Assignment z = new Assignment( rs.getString(1), rs.getString(2), rs.getBoolean(3),
						rs.getDouble(4), rs.getInt(5), rs.getInt(6)); 
				allAssignments.add(z);
			}
			
			   
			}
			
			catch(Exception e)
				{ System.out.println(e);} 
	}
	
	
	private void retrieveStudents()
	{
		try{  
			
			//here sonoo is database name, root is username and password 
			Statement stmt=con.createStatement();  
			ResultSet rs=stmt.executeQuery("select * from Student");  
			while(rs.next())
			{
				Student z = new Student(rs.getInt(1), rs.getString(2)); 
				allStudents.add(z);
			}
			
			   
			}
			
			catch(Exception e)
				{ System.out.println(e);} 
	}
	
	private void retrieveInstructors()
	{
		try{  
			
			Statement stmt=con.createStatement();  
			ResultSet rs=stmt.executeQuery("select * from Instructor");  
			while(rs.next())
			{
				Instructor z = new Instructor(rs.getInt(1), rs.getString(2)); 
				allInstructors.add(z);
			}
			
			   
			}
			
			catch(Exception e)
				{ System.out.println(e);} 
	}
	
	private void retrieveCourses()
	{
		try{  
			
			Statement stmt=con.createStatement();  
			ResultSet rs=stmt.executeQuery("select * from Course");  
			while(rs.next())
			{
				List<Student> studentsInCourse = new ArrayList<Student>();
				List<Assignment> assignments = new ArrayList<Assignment>();
				
				int courseID = rs.getInt(1);
				String courseName = rs.getString(2);
				int courseInstructor = rs.getInt(3);
				
				
				Statement stat2 =con.createStatement();  
				ResultSet rs2=stat2.executeQuery("select * from studentsinCourse");  
				while(rs2.next())
				{
					if(rs2.getInt(2) == courseID)
					{
						for(Student x: allStudents)
						{
							if(x.getID() == rs2.getInt(1))
							{
								studentsInCourse.add(x);
							}
						}
					}
				}
				
				
				for(Assignment a: allAssignments)
				{
					if(a.getCourseId() == courseID)
					{
						assignments.add(a);
					}
				}
				
				
				Course z = new Course(courseID, courseName, studentsInCourse, assignments, courseInstructor);
				allCourses.add(z);
			}
			
			   
			}
			
			catch(Exception e)
				{ System.out.println(e);} 
	}
	
}
