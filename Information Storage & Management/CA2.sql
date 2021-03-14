CREATE TABLE Pubs (
	PLN char(5),
	PubName varchar(20),
	PCounty varchar(20),
	PRIMARY KEY (PLN)
);

CREATE TABLE  NeighbourCounty(
	County1 varchar(20),
	County2 varchar(20)
);

CREATE TABLE Person(
	PPSN int(10),
	PName varchar(20),
	PCounty varchar(20),
	Age int(3),
	DailyPubLimit int(3),
	PRIMARY KEY (PPSN)
);

CREATE TABLE Visit(
	PLN char(5),
	PPSN int(10),
	StartDateOfVisit datetime,
	EndDateOfVisit datetime,
	FOREIGN KEY (PLN) REFERENCES Pubs(PLN),
	FOREIGN KEY (PPSN) REFERENCES Person(PPSN)
	
);

CREATE TABLE Covid_Diagnosis(
	PPSN int(10),
	DiagnosisDate date,
	IsolationEndDate date,
	FOREIGN KEY (PPSN) REFERENCES Person(PPSN)
);


CREATE VIEW COVID_NUMBERS AS
SELECT DISTINCT p.PCounty County, COUNT(*) Cases FROM Covid_Diagnosis as c JOIN Person as p
WHERE p.PPSN = c.PPSN
GROUP BY p.PCounty;


CREATE TRIGGER Add_Visit BEFORE INSERT ON Visit
FOR EACH ROW
	BEGIN
		
		DECLARE counter INT; -- Counter for max pub limit
		SET counter = (SELECT COUNT(*) FROM Visit WHERE PPSN = new.PPSN AND StartDateOfVisit > new.StartDateOfVisit - INTERVAL 1 DAY);
		
		-- Checks if person should be self isolating
		IF EXISTS (SELECT PPSN FROM Covid_Diagnosis WHERE new.PPSN = PPSN AND 
			new.StartDateOfVisit > DiagnosisDate AND new.StartDateOfVisit < IsolationEndDate) THEN
			SIGNAL SQLSTATE '45000'
			SET MESSAGE_TEXT = 'Person should be in isolation, not out drinking';
		-- Checks if person is going to a pub in their county/neighbouring county
		ELSEIF NOT EXISTS
        (SELECT PLN FROM Pubs WHERE PLN = new.PLN AND PCounty IN 
        (SELECT DISTINCT n.County1 FROM NeighbourCounty AS n JOIN Person AS p
        WHERE p.PPSN = new.PPSN AND (p.PCounty = n.County1 OR p.PCounty = n.County2))) THEN
			SIGNAL SQLSTATE '45000'
			SET MESSAGE_TEXT = 'Person is trying to visit a pub outside of their county or their county''s neighbours';	
		-- Checks if person is already visiting a pub
		ELSEIF EXISTS (	SELECT * FROM Visit WHERE PPSN = new.PPSN AND ((new.StartDateOfVisit >= StartDateOfVisit AND new.StartDateOfVisit <= EndDateOfVisit) OR (new.EndDateOfVisit >= StartDateOfVisit AND new.EndDateOfVisit <= EndDateOfVisit) OR (new.StartDateOfVisit <= StartDateOfVisit AND new.EndDateOfVisit >= EndDateOfVisit)) ) THEN 
			SIGNAL SQLSTATE '45000'
			SET MESSAGE_TEXT = 'Person is already visiting a pub at this time';
		-- Checks if person has exceeded their limit of pubs
		ELSEIF counter > (SELECT DailyPubLimit FROM Person WHERE PPSN = new.PPSN) THEN 
			SIGNAL SQLSTATE '45000'
			SET MESSAGE_TEXT = 'Person is trying to visit too many pubs in one day!';
		END IF;
		
	END;


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
("L1234", 1, "2020/02/10 10:00", "2020/02/10 11:00"),
("L1234", 1, "2020/12/08 11:00", "2020/12/08 11:35"),
("L2345", 3, "2020/12/03 11:00", "2020/12/03 11:50");
