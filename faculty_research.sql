DROP DATABASE IF EXISTS faculty_research;

CREATE DATABASE faculty_research;

USE faculty_research;

DROP TABLE IF EXISTS department;
CREATE TABLE department(
    DepartmentID int(10) NOT NULL,
    DepartmentName varchar(50),
    OfficeRoomNumber varchar(10),
    PRIMARY KEY (DepartmentID)
);

DROP TABLE IF EXISTS faculty;
CREATE TABLE faculty(
    FacultyID int(10) NOT NULL,
    DepartmentID int(10),
    FirstName varchar(20),
    LastName varchar(20),
    Email varchar(30),
    Interest varchar(30),
    OfficeBuilding varchar(10),
    OfficePhoneNumber varchar(20),
    OfficeRoomNumber varchar(10),
    PRIMARY KEY (FacultyID),
    CONSTRAINT FK_faculty_department FOREIGN KEY (DepartmentID) REFERENCES department(DepartmentID)
);

DROP TABLE IF EXISTS student;
CREATE TABLE student(
    StudentID int(10) NOT NULL,
    DepartmentID int(10),
    FirstName varchar(20),
    LastName varchar(20),
    Email varchar(30),
    SchoolYear varchar(20),
    Major varchar(30),
    PRIMARY KEY(StudentID),
    CONSTRAINT FK_student_department FOREIGN KEY(DepartmentID) REFERENCES department(DepartmentID)
);


DROP TABLE IF EXISTS project;
CREATE TABLE project(
    ProjectID int(10) NOT NULL AUTO_INCREMENT,
    FacultyID int(10),
    ProjectName varchar(255),
    ProjectDescription varchar(255),
    Budget  decimal(7,2),
    StartDate date,
    EndDate date,
    StudentID int(11),
    PRIMARY KEY(ProjectID),
    CONSTRAINT FK_project_faculty FOREIGN KEY(FacultyID) REFERENCES faculty(FacultyID),
    CONSTRAINT FK_project_student FOREIGN KEY(StudentID) REFERENCES student(StudentID)
);


--
-- Dumping data for table 'department'
--
-- Fields: DepartmentID, DepartmentName, OfficeNumber
INSERT INTO department VALUES(1, "Computer Science","GOL-3005"); 
INSERT INTO department VALUES(2, "Computing Security","GOL-2120"); 
INSERT INTO department VALUES(3, "Information Sciences & Technologies","GOL-2100"); 
INSERT INTO department VALUES(4, "School of Interactive Games & Media","GOL-2145"); 
INSERT INTO department VALUES(5, "Software Engineering","GOL-1690"); 


--
-- Dumping data for table 'faculty'
--
-- Fields: FacultyID, DepartmentID, FirstName, LastName,Email,Interest,OfficeBuilding,OfficePhoneNumber,OfficeRoomNumber
INSERT INTO faculty VALUES(1, 1, "Reynold", "Bailey", "rjb@cs.rit.edu", "CS", "Golisano", "585-475-6181", "GOL-3005");
INSERT INTO faculty VALUES(2, 1, "Ivona", "Bezakova", "ib@cs.rit.edu", "CS", "Golisano", "585-475-4526", "GOL-3645");
INSERT INTO faculty VALUES(3, 2, "Giovani", "Abuaitah", "graics@rit.edu", "CSEC", "Golisano", "585-475-4316", "GOL-2321");
INSERT INTO faculty VALUES(4, 2, "Hrishikesh", "Archarya", "hbaics@rit.edu", "CSEC", "Golisano", "585-475-2801", "GOL-2647");
INSERT INTO faculty VALUES(5, 3, "Dan", "Bogaard", "dsbics@rit.edu", "ISTE", "Golisano", "585-475-5231", "GOL-2111");
INSERT INTO faculty VALUES(6, 3, "Michael", "Floeser", "Michael.Floeser@rit.edu", "ISTE", "Golisano", "585-475-7031", "GOL-2669");
INSERT INTO faculty VALUES(7, 4, "David", "Schwartz", "disvks@rit.edu", "IGM", "Golisano", "585-475-5521", "GOL-2157");
INSERT INTO faculty VALUES(8, 4, "Jessica", "Bayliss", "jdbics@rit.edu", "IGM", "Golisano", "585-475-2507", "GOL-2153");
INSERT INTO faculty VALUES(9, 5, "Travis", "Desell", "tjdvse@rit.edu", "SE", "Golisano", NULL, "GOL-1559");
INSERT INTO faculty VALUES(10, 5, "Scott", "Hawker", "hawker@mail.rit.edu", "SE", "Golisano", "585-475-2705", "GOL-1696");	



--
-- Dumping data for table 'student'
--
-- Fields: StudentID, DepartmentID, FirstName, LastName, Email, SchoolYear, Major
INSERT INTO student VALUES(1, 1, 'John', 'Smith','js1434@rit.edu','Sophomore','CS');
INSERT INTO student VALUES(2, 2, 'George', 'Brown','gb6745@rit.edu','Junior','CSEC');
INSERT INTO student VALUES(3, 3, 'Carmen', 'Tang','ct7891@rit.edu','Freshman','IST');
INSERT INTO student VALUES(4, 4, 'Laura', 'Diaze','ldz3142@rit.edu','Freshman','IGM');
INSERT INTO student VALUES(5, 5, 'Fred', 'Amarty','fd5930@rit.edu','Junior','SE');
INSERT INTO student VALUES(6, 1, 'Ronald', 'Sowah','rs9030@rit.edu','Senior','CS');
INSERT INTO student VALUES(7, 2, 'Desley','Brown','db8930@rit.edu','Freshman','CSEC');
INSERT INTO student VALUES(8, 3, 'Tiffany', 'Dubon','td8197@rit.edu','Sophomore','IST');
INSERT INTO student VALUES(9, 4, 'Lauren', 'Campbell','lc2543@rit.edu','Freshman','IGM');
INSERT INTO student VALUES(10, 5, 'Sam', 'Illesamni','smoi3030@rit.edu','Junior','SE');


--
-- Dumping data for table 'project'
--
-- Fields: ProjectID, StudentID, FacultyID, ProjectName, ProjectDescription, Budget, StartDate, EndDate
INSERT INTO project (projectid, facultyid, projectname, projectdescription, budget, startdate, enddate) VALUES(1, 1, 'Lookup or Learn: A Grounded Theory Approach to the Student-Task', 'Proceedings of the 9th International Conference on Education and Information Systems, Technologies and Applications', 1000.00, '2018-08-27','2018-12-11');
INSERT INTO project (projectid, facultyid, projectname, projectdescription, budget, startdate, enddate) VALUES(2, 2, 'Progress on Website Accessibility?' , 'ACM Transactions on the Web', 1000.00, '2017-08-22','2017-12-08');
INSERT INTO project (projectid, facultyid, projectname, projectdescription, budget, startdate, enddate) VALUES(3, 3, 'Accessibility Support with the ACCESS Framework', 'International Journal of Human Computer Interaction', 1000.00, '2017-01-22','2017-05-08');
INSERT INTO project (projectid, facultyid, projectname, projectdescription, budget, startdate, enddate) VALUES(4,4,'Digital Motherhood: How Does Technology Support New Mothers?', 'Proceedings of the ACM SIGCHI Conference on Human Factors in Computing Systems', 1000.00, '2017-01-27','2017-08-08');
INSERT INTO project (projectid, facultyid, projectname, projectdescription, budget, startdate, enddate) VALUES(5,5,'A Protocol for Software-Supported Interaction with Broadcast Debates','Proceedings of the Open Digital, Fourth Annual Digital Economy All Hands Conference', 1000.00, '2016-01-23','2016-05-10');
INSERT INTO project (projectid, facultyid, projectname, projectdescription, budget, startdate, enddate) VALUES(6,6,'Older Adults and Social Networking Sites: Developing Recommendations for Inclusive Design','Proceedings of the Open Digital, Fourth Annual Digital Economy All Hands Conference', 1000.00, '2016-08-30','2016-12-14');
INSERT INTO project (projectid, facultyid, projectname, projectdescription, budget, startdate, enddate) VALUES(7,7,'Implementation and Evaluation of Animation Controls Sufficient for Conveying ASL Facial Expressions','Proceedings of the 15th International ACM SIGACCESS Conference on Computers and Accessibility', 1000.00, '2017-02-01','2017-05-15');
INSERT INTO project (projectid, facultyid, projectname, projectdescription, budget, startdate, enddate) VALUES(8,8,'Release of Experimental Stimuli and Questions for Evaluating Facial Expressions in Animations of American Sign Language',' Proceedings of the 6th Workshop on the Representation and Processing of Sign Languages: Beyond the Manual Channel, The 9th International Conference on Language Resources and Evaluation', 1000.00, '2018-08-31','2018-12-02');
INSERT INTO project (projectid, facultyid, projectname, projectdescription, budget, startdate, enddate) VALUES(9,9, 'Using a Low-Cost Open Source Hardware Development Platform in Teaching Young Students Programming Skills','Proceedings of the SIGITE', 1000.00, '2017-01-31','2017-05-04');
