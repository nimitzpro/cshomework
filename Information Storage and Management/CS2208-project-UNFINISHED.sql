CREATE TABLE Pubs(
	PRIMARY KEY PLN char(5),
	PubName varchar(20),
	FOREIGN KEY PCounty varchar(20)
);

CREATE TABLE  NeighbourCounty(
	PRIMARY KEY County1,
	FOREIGN KEY County2
);

CREATE TABLE Person(
	PRIMARY KEY PPSN int(10),
	PName varchar(20),
	FOREIGN KEY PCounty varchar(20),
	Age int(3),
	DailyPubLimit int(3)
);

CREATE TABLE Visit(
	FOREIGN KEY PLN char(5),
	FOREIGN KEY PPSN int(10),
	StartDateOfVisit datetime,
	EndDateOfVisit datetime
);

CREATE TABLE Covid_Diagnosis(
	FOREIGN KEY PPSN int(10),
	DiagnosisDate date,
	IsolationEndDate date
);

INSERT INTO Pubs VALUES
("L1234", "Murphy's", "Cork"),
("L2345", "Joe's", "Limerick"),
("L3456", "BatBar", "Kerry");

INSERT INTO NeighbourCounty VALUES
("Cork", "Limerick"),
("Limerick", "Cork"),
("Cork", "Kerry"),
("Kerry", "Cork");

INSERT INTO Person VALUES
(1, "Liza", "Cork", 22, 5),
(2, "Alex", "Limerick", 19, 7),
(3, "Tom", "Kerry", 23, 10),
(4, "Peter", "Cork", 39, 8);

INSERT INTO Visit VALUES
("L1234", 1, "2020/10/02 10AM", "2020/10/02 11AM"),
("L1234", 1, "2020/08/12 11AM", "2020/08/12 11:35AM"),
("L2345", 3, "2020/03/12 11AM", "2020/03/12 11:50AM");

INSERT INTO Covid_Diagnosis VALUES
(2, "2020/11/02", "2020/21/02");

CREATE VIEW COVID_NUMBERS AS
SELECT c.PCounty, COUNT(c.PCounty) FROM Person as p
JOIN Covid_Diagnosis as c
WHERE p.ppsn = c.ppsn



CREATE TRIGGER Add_Visit BEFORE INSERT ON Visit
	SELECT * FROM Covid_Diagnosis
	FOR EACH ROW
	BEGIN
		IF(new.PPSN == Covid_Diagnosis.PPSN AND 
			new.StartDateOfVisit > Covid_Diagnosis.DiagnosisDate AND new.StartDateOfVisit < Covid_Diagnosis.IsolationEndDates) THEN
			SIGNAL SQLSTATE '45000'
   			SET MESSAGE_TEXT = 'Person should be in isolation, not out drinking';
		END IF;
	
		IF(new.PLN NOT IN
		(SELECT p.PLN FROM Pubs WHERE p.PCounty IN 
		(SELECT n.County1, n.County2 FROM Person AS p JOIN NeighbourCounty AS n
		WHERE p.PCounty = n.County1))) THEN
		
		SIGNAL SQLSTATE '45000'
		SET MESSAGE_TEXT = 'Person is trying to visit a pub outside of their county or their county''s neighbours'		
		END IF;
	
	END