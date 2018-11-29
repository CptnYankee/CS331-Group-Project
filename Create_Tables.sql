
drop table MaintenanceRequest;
drop table Room;
drop table Resident;
drop table Applicant;
drop table Person;
drop table AdminAccnt;


CREATE TABLE AdminAccnt(
	StaffID INT(5) NOT NULL,
	Password VARCHAR(15) NOT NULL,
	Name VARCHAR(50) NOT NULL,
	Department VARCHAR(20),
	PhoneNum INT(11),
	Gender CHAR(1),
	Position VARCHAR(20),
	Primary Key (StaffID)
);

CREATE TABLE Person(
	SSN INT(9) NOT NULL,
	StudentID INT(10) UNSIGNED NOT NULL,
    Name VARCHAR(20),
	Alumni Bool,
	Graduation_Year YEAR(4),
	Degree VARCHAR(20),
	Student Bool,
	Major VARCHAR(20),
	GradeLevel ENUM('Freshman','Sophmore','Junior','Senior'),
	PermAddress VARCHAR(255) NOT NULL,
	DOB DATE,
	Phone INT(11),
	MaritalStatus BOOLEAN,
	SpouseID INT(10) DEFAULT NULL,
	Email VARCHAR(25),
	Gender CHAR(1) NOT NULL,
	PRIMARY KEY (StudentID)
);

CREATE TABLE Resident(
	StudentID INT(10) UNSIGNED NOT NULL,
	Password VARCHAR(15),
	ResName VARCHAR(20) NOT NULL,
	Gender CHAR(1) NOT NULL,
	MaritalStatus BOOLEAN,
	RoomNum TINYINT(4) UNSIGNED NOT NULL,
    BuildingID TINYINT(4) NOT NULL,
	Address VARCHAR(255) NOT NULL,
	Phone INT(11),
	Major VARCHAR(20),
    PrefRoomate INT(10),
	Primary Key (StudentID),
	FOREIGN Key (StudentID) REFERENCES Person(StudentID)
);

CREATE TABLE Applicant(
	StudentID INT(10) UNSIGNED NOT NULL,
	Password VARCHAR(15),
	SSN int(9) NOT NULL,
	AppName VARCHAR(20) NOT NULL,
	Gender CHAR(1) NOT NULL,
	MaritalStatus BOOL DEFAULT False,
	Address VARCHAR(255) NOT NULL,
	Phone INT(11),
	Major VARCHAR(20),
	PrefRoomate INT(10) UNSIGNED,
	RoomPref1 TEXT(30),
	RoomPref2 TEXT(30),
	RoomPref3 TEXT(30),
    
	Primary Key (StudentID,SSN),
	FOREIGN KEY (StudentID) REFERENCES Person(StudentID)
);
CREATE TABLE Room(
	BuildingID VARCHAR(5) NOT NULL,
	RoomNum TINYINT(4) UNSIGNED NOT NULL,
	FloorNum TINYINT(4) NOT NULL,
	Address VARCHAR(255) NOT NULL,
    Marriage BOOLEAN,
    Style TEXT(30),
	SpaceAvailable ENUM('0','1','2','3','4'),
    Primary Key (BuildingID,RoomNum,FloorNum)
);

CREATE TABLE MaintenanceRequest(
	/*Easy Primary Key*/
	RequestID SMALLINT(5) NOT NULL AUTO_INCREMENT,
	SubmitterID INT(10) UNSIGNED NOT NULL,
	BuildingID VARCHAR(5) NOT NULL,
	RoomNum TINYINT(4) UNSIGNED NOT NULL,
	FloorNum TINYINT(4) NOT NULL,
	SubmissionDate DATE NOT NULL,
	ClearDate DATE DEFAULT NULL,
	Description TEXT(30),
	ResidentName VARCHAR(50),
	Employee VARCHAR(50),
	PRIMARY KEY (RequestID),
	FOREIGN KEY (SubmitterID) REFERENCES Person(StudentID)
);
