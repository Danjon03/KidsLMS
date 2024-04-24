package kidsLMS;

import java.util.List;

public class User {
	
	/*
	 * The User class is the parent class for Student, Instructor, and potentially Parent
	 * 
	 * Getters. Does not need setters
	 * Login only uses ID
	 * 
	 * 
	 * 
	 * */
	
	private int ID;
	private String Name;
	
	public User(int id, String name)
	{
		ID=id;
		Name=name;
	}
	
	
	public int getID()
	{
		return ID;
	}
	
	public String getName()
	{
		return Name;
	}
	
	//Methods we need to write//
	
	

	
	
}
