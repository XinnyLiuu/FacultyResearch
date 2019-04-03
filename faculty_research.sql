DROP DATABASE IF EXISTS faculty_research;

CREATE DATABASE faculty_research;

USE faculty_research;

DROP TABLE IF EXISTS department;
CREATE TABLE department(
    DepartmentID int(11) NOT NULL,
    DepartmentName varchar(20),
    OfficeRoomNumber char(10),
    PRIMARY KEY (DepartmentID)
);

DROP TABLE IF EXISTS faculty;
CREATE TABLE faculty(
    FacultyID int(11) NOT NULL,
    DepartmentID int(11),
    FirstName varchar(20),
    LastName varchar(20),
    Email varchar(20),
    Interest varchar(30),
    OfficeBuilding char(5),
    OfficePhoneNumber varchar(10),
    OfficeRoomNumber char(5),
    PRIMARY KEY (FacultyID),
    FOREIGN KEY (DepartmentID) REFERENCES department(DepartmentID)
);

DROP TABLE IF EXISTS project;
CREATE TABLE project(
    ProjectID int(11) NOT NULL,
    FacultyID int(11),
    ProjectName varchar(20),
    ProjectDescription varchar(80),
    Budget  decimal(7,2),
    StartDate date,
    EndDate date,
    PRIMARY KEY(ProjectID),
    FOREIGN KEY(FacultyID) REFERENCES faculty(FacultyID)
);

DROP TABLE IF EXISTS student;
CREATE TABLE student(
    StudentID int(11) NOT NULL,
    DepartmentID int(11),
    FirstName varchar(20),
    LastName varchar(20),
    Email varchar(20),
    SchoolYear varchar(20),
    Major varchar(30),
    PRIMARY KEY(StudentID),
    FOREIGN KEY(DepartmentID) REFERENCES department(DepartmentID)
);



--
-- Dumping data for table 'faculty'
--

-- Fields: FacultyID, DepartmentID, FirstName, LastName,Email,Interest,OfficeBuilding,OfficePhoneNumber,OfficeRoomNumber
INSERT INTO faculty VALUES();




--
-- Dumping data for table 'department'
--

-- Fields: DepartmentID, DepartmentName, OfficeNumber
INSERT INTO department VALUES(); 




--
-- Dumping data for table 'project'
--

-- Fields: ProjectID, FacultyID, ProjectName, ProjectDescription, Budget, StartDate, EndDate
INSERT INTO project VALUES();




--
-- Dumping data for table 'student'
--

-- Fields: StudentID, DepartmentID, FirstName, LastName, Email, SchoolYear, Major
INSERT INTO student VALUES();


