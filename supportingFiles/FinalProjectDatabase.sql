use KidsLMS;

drop table Student;
create table Student(
	ID int Not Null primary key auto_increment,
    Name varchar(50)
);

drop table Instructor;
create table Instructor(
	ID int Not Null primary key auto_increment,
    Name varchar(50)
);

drop table Course;
create table Course(
ID int not Null primary key auto_increment,
Name varchar(50),
InstructorID int

);

drop table studentsIncourse;
create table studentsInCourse
(
	StudentID int,
    CourseID int
);

drop table assignments;
create table assignments
(
    Title varchar(50),
    Descr varchar(200),
    completed bool,
    grade double,
    studentID int,
    courseID int
);

-- insert into assignments( Title, Descr, completed, grade, studentID, CourseID) Values( "Programming Essay", "Write a 2 page essay on computer programming languages", 
-- false, 0.0, 1, 1);

-- insert into studentsInCourse(StudentID, CourseID) Values(1, 1);
-- insert into studentsInCourse(StudentID, CourseID) Values(1, 2);
-- insert into studentsInCourse(StudentID, CourseID) Values(2, 3);
-- insert into studentsInCourse(StudentID, CourseID) Values(3, 4);
-- insert into studentsInCourse(StudentID, CourseID) Values(4, 5);


insert into Student(Id, Name) Values(null, "Will");
insert into Student(Id, Name) Values(null, "Joel");
-- insert into Student(Id, Name) Values(null, "Joseph");
-- insert into Student(Id, Name) Values(null, "Gabe");
-- insert into Student(Id, Name) Values(null, "Kumar");

insert into Instructor(Id, Name) Values(null, "Professor McGonagal");
-- insert into Instructor(Id, Name) Values(null, "John");
-- insert into Instructor(Id, Name) Values(null, "Julius");
-- insert into Instructor(Id, Name) Values(null, "Santiago");
-- insert into Instructor(Id, Name) Values(null, "Bryce");

-- insert into Course(Id, Name, instructorID) Values(null, "Algebra 1", 0);
-- insert into Course(Id, Name, instructorID) Values(null, "Algebra 2", 2);
-- insert into Course(Id, Name, instructorID) Values(null, "Pre-Algebra", 1);
-- insert into Course(Id, Name, instructorID) Values(null, "English", 3);
-- insert into Course(Id, Name, instructorID) Values(null, "Math", 3);
-- insert into Course(Id, Name, instructorID) Values(null, "Science", 4);


