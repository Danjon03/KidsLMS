package kidsLMS;

import java.util.ArrayList;
import java.util.List;

public class Course {
	
	/*
	 * Class that stores all information about a given course
	 * 
	 * 
	 */
	
	//Information that is given directly by the course table of the database
	private int courseId;
	private String courseName;
	private int instructorID;
	
	//Information given by a StudentCourse table
	List<Student> membersOfCourse = new ArrayList<Student>();
	List<Assignment> allCourseAssignments = new ArrayList<Assignment>();
	
	
	public Course(int id, String name, List<Student> users, List<Assignment> assign, int instruc)
	{
		courseId = id;
		courseName = name;
		membersOfCourse = users;
		allCourseAssignments = assign;
		instructorID = instruc;
	}
	
	public int getId()
	{
		return courseId;
	}
	
	public String getCourseName()
	{
		return courseName;
	}
	
	public int getInstructorID()
	{
		return instructorID;
	}
	
	//Functions to create//
	
	//print all members(students and teachers)
	public void printAllMembers()
	{
		for(User x: membersOfCourse)
		{
			System.out.println(x.getName());
		}
	}
	
	//print all assignments
	public void printAllStudentAssignmentsP(int studentID)
	{
		
	}
}
