package kidsLMS;

public class Assignment {
	
	private int id;
	private String title;
	private String description;
	private boolean completed;
	private double grade;
	private int studentId;
	private int courseId;
	
	
	public Assignment( String t, String d, boolean c, double g, int s, int cid)
	{
		
		title=t;
		description =d;
		completed=c;
		grade=g;
		studentId = s;
		courseId=cid;
	}

	public String getDescription()
	{
		return description;
	}
	
	public void setDescription(String d)
	{
		description = d;
	}
	
	public int getStudentId()
	{
		return studentId;
	}
	
	public int getCourseId()
	{
		return courseId;
	}
	
	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public boolean isCompleted() {
		return completed;
	}


	public void setCompleted(boolean completed) {
		this.completed = completed;
	}


	public double getGrade() {
		return grade;
	}


	public void setGrade(double grade) {
		this.grade = grade;
	}
	
	
	
}
