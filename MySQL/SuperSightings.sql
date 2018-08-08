DROP DATABASE IF EXISTS SuperSightings;

CREATE DATABASE SuperSightings;

USE SuperSightings;

-- DROP DATABASE IF EXISTS SuperSightingsTest;

-- CREATE DATABASE SuperSightingsTest;

-- USE SuperSightingsTest;

-- CREATE Entity
CREATE TABLE IF NOT EXISTS Entity
(EntityID SMALLINT UNSIGNED AUTO_INCREMENT,
EntityName VARCHAR(50) NOT NULL,
EntityDesc VARCHAR(255) NOT NULL,
IsHero BOOL NOT NULL DEFAULT 1,
PRIMARY KEY(EntityID)
);

-- CREATE Power
CREATE TABLE IF NOT EXISTS `Power`
(PowerID INT UNSIGNED NOT NULL AUTO_INCREMENT,
PowerDesc VARCHAR(50) NOT NULL,
PRIMARY KEY (PowerID)
);

-- CREATE EntityPower
CREATE TABLE IF NOT EXISTS EntityPower
(EntityID SMALLINT UNSIGNED,
PowerID INT UNSIGNED,
PRIMARY KEY (EntityID, PowerID),
CONSTRAINT fk_PowerEntityID FOREIGN KEY (EntityID) REFERENCES Entity(EntityID),
CONSTRAINT fk_PowerID FOREIGN KEY (PowerID) REFERENCES `Power`(PowerID)
);

-- CREATE Organization
CREATE TABLE IF NOT EXISTS Organization
(OrgID SMALLINT UNSIGNED AUTO_INCREMENT,
OrgName VARCHAR(50) NOT NULL,
OrgDesc VARCHAR(255) NOT NULL,
OrgAddress VARCHAR(75) NOT NULL,
OrgEmail VARCHAR(50),
OrgPhone VARCHAR(15),
PRIMARY KEY(OrgID)
);

-- CREATE EntityOrganization
CREATE TABLE IF NOT EXISTS EntityOrganization
(EntityID SMALLINT UNSIGNED,
OrgID SMALLINT UNSIGNED,
PRIMARY KEY(EntityID, OrgID),
CONSTRAINT fk_OrgID FOREIGN KEY (OrgID) REFERENCES Organization(OrgID),
CONSTRAINT fk_OrgEntityID FOREIGN KEY (EntityID) REFERENCES Entity(EntityID)
);

-- CREATE Location
CREATE TABLE  IF NOT EXISTS Location
(LocID INT UNSIGNED AUTO_INCREMENT,
LocName VARCHAR(50) NOT NULL,
LocDesc VARCHAR(255) NOT NULL,
LocAddress VARCHAR(75),
LocLatitude DECIMAL(8,6) NOT NULL,
LocLongitude DECIMAL(9,6) NOT NULL,
PRIMARY KEY (LocID)
);

-- CREATE Sighting
CREATE TABLE IF NOT EXISTS Sighting
(SightingID INT UNSIGNED AUTO_INCREMENT,
LocID INT UNSIGNED NOT NULL,
SightingDateTime DATETIME NOT NULL,
PRIMARY KEY (SightingID),
CONSTRAINT fk_LocID FOREIGN KEY (LocID) REFERENCES Location(LocID)
);

-- CREATE SightingEntity
CREATE TABLE IF NOT EXISTS SightingEntity
(SightingID INT UNSIGNED AUTO_INCREMENT,
EntityID SMALLINT UNSIGNED,
PRIMARY KEY (SightingID, EntityID),
CONSTRAINT fk_SightingID FOREIGN KEY (SightingID) REFERENCES Sighting(SightingID),
CONSTRAINT fk_SightingEntityID FOREIGN KEY (EntityID) REFERENCES Entity(EntityID)
);