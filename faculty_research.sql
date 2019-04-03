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
-- CS - 21
INSERT INTO faculty VALUES(11, 21, "Reynold", "Bailey", "rjb@cs.rit.edu", "CS", "Golisano", "585-475-6181", "GOL-3005");
INSERT INTO faculty VALUES(12, 21, "Ivona", "Bezakova", "ib@cs.rit.edu", "CS", "Golisano", "585-475-4526", "GOL-3645");

-- CSEC - 22
INSERT INTO faculty VALUES(13, 22, "Giovani", "Abuaitah", "graics@rit.edu", "CSEC", "Golisano", "585-475-4316", "GOL-2321");
INSERT INTO faculty VALUES(14, 22, "Hrishikesh", "Archarya", "hbaics@rit.edu", "CSEC", "Golisano", "585-475-2801", "GOL-2647");

-- IST - 23
INSERT INTO faculty VALUES(15, 23, "Dan", "Bogaard", "dsbics@rit.edu", "ISTE", "Golisano", "585-475-5231", "GOL-2111");
INSERT INTO faculty VALUES(16, 23, "Michael", "Floeser", "Michael.Floeser@rit.edu", "ISTE", "Golisano", "585-475-7031", "GOL-2669");

-- IGM - 24
INSERT INTO faculty VALUES(17, 24, "David", "Schwartz", "disvks@rit.edu", "IGM", "Golisano", "585-475-5521", "GOL-2157");
INSERT INTO faculty VALUES(18, 24, "Jessica", "Bayliss", "jdbics@rit.edu", "IGM", "Golisano", "585-475-2507", "GOL-2153");

-- SE - 25
INSERT INTO faculty VALUES(19, 25, "Travis", "Desell", "tjdvse@rit.edu", "SE", "Golisano", NULL, "GOL-1559");
INSERT INTO faculty VALUES(110, 25, "Scott", "Hawker", "hawker@mail.rit.edu", "SE", "Golisano", "585-475-2705", "GOL-1696");	



--
-- Dumping data for table 'department'
--

-- Fields: DepartmentID, DepartmentName, OfficeNumber
INSERT INTO department VALUES(); 




--
-- Dumping data for table 'project'
--

-- Fields: ProjectID, FacultyID, ProjectName, ProjectDescription, Budget, StartDate, EndDate
INSERT INTO project VALUES(41, 11, 'Lookup or Learn: A Grounded Theory Approach to the Student-Task', 'Proceedings of the 9th International Conference on Education and Information Systems, Technologies and Applications', '2018-08-27','2018-12-11');
INSERT INTO project VALUES(42, 12, 'Progress on Website Accessibility?' , 'ACM Transactions on the Web','2017-08-22','2017-12-08');
INSERT INTO project VALUES(43, 13, 'Accessibility Support with the ACCESS Framework', 'International Journal of Human Computer Interaction','2017-01-22','2017-05-08');
INSERT INTO project VALUES(44,14,'Digital Motherhood: How Does Technology Support New Mothers?', 'Proceedings of the ACM SIGCHI Conference on Human Factors in Computing Systems','2017-01-27','2017-08-08');
INSERT INTO project VALUES(45,15,'A Protocol for Software-Supported Interaction with Broadcast Debates','Proceedings of the Open Digital, Fourth Annual Digital Economy All Hands Conference','2016-01-23','2016-05-10');
INSERT INTO project VALUES(46,16,'Older Adults and Social Networking Sites: Developing Recommendations for Inclusive Design','Proceedings of the Open Digital, Fourth Annual Digital Economy All Hands Conference','2016-08-30','2016-12-14');
INSERT INTO project VALUES(47,17,'Implementation and Evaluation of Animation Controls Sufficient for Conveying ASL Facial Expressions','Proceedings of the 15th International ACM SIGACCESS Conference on Computers and Accessibility','2017-02-01','2017-05-15');
INSERT INTO project VALUES(48,18,'Release of Experimental Stimuli and Questions for Evaluating Facial Expressions in Animations of American Sign Language',' Proceedings of the 6th Workshop on the Representation and Processing of Sign Languages: Beyond the Manual Channel, The 9th International Conference on Language Resources and Evaluation','2018-08-31','2018-12-02');
INSERT INTO project VALUES(49,19, 'Using a Low-Cost Open Source Hardware Development Platform in Teaching Young Students Programming Skills','Proceedings of the SIGITE','2017-01-31','2017-05-04');
INSERT INTO project VALUES(410,110,'Teaching Android Malware Behaviors for Android Platform Using Interactive Labs','Proceedings of the ASIA 2013','2017-08-25','2017-12-13');



--
-- Dumping data for table 'student'
--

-- Fields: StudentID, DepartmentID, FirstName, LastName, Email, SchoolYear, Major
INSERT INTO student VALUES();


