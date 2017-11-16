-- MySQL Script generated by MySQL Workbench
-- Wed Nov  1 18:19:22 2017
-- Model: New Model    Version: 1.0
SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------


-- -----------------------------------------------------
-- Table `Address`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Address` ;

CREATE TABLE IF NOT EXISTS `Address` (
  `AddressID` INT NOT NULL AUTO_INCREMENT,
  `Street` VARCHAR(45) NULL,
  `City` VARCHAR(45) NULL,
  `State` VARCHAR(45) NULL,
  `Zip` VARCHAR(45) NULL,
  `Country` VARCHAR(45) NULL,
  PRIMARY KEY (`AddressID`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Person`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Person` ;

CREATE TABLE IF NOT EXISTS `Person` (
  `PersonID` INT NOT NULL AUTO_INCREMENT,
  `PersonCode` VARCHAR(45) NULL DEFAULT NULL,
  `PersonFirstName` VARCHAR(45) NULL,
  `PersonLastName` VARCHAR(45) NULL,
  `AddressID` INT NOT NULL,
  PRIMARY KEY (`PersonID`),
  INDEX `AddressID_idx` (`AddressID` ASC),
  CONSTRAINT `fk_AddressID_from_AddressToPerson`
    FOREIGN KEY (`AddressID`)
    REFERENCES `Address` (`AddressID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Customer`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Customer` ;

CREATE TABLE IF NOT EXISTS `Customer` (
  `CustomerID` INT NOT NULL AUTO_INCREMENT,
  `CustomerCode` VARCHAR(45) NULL DEFAULT NULL,
  `CustomerName` VARCHAR(45) NULL,
  `CustomerType` VARCHAR(45) NULL,
  `CustomerPrimaryContactID` INT NOT NULL,
  `CustomerAddressID` INT NOT NULL,
  PRIMARY KEY (`CustomerID`),
  INDEX `CustomerAddressID_idx` (`CustomerAddressID` ASC),
  INDEX `CustomerPrimaryContactID_idx` (`CustomerPrimaryContactID` ASC),
  CONSTRAINT `fk_CustomerAddressID_fromAddressToCustomer`
    FOREIGN KEY (`CustomerAddressID`)
    REFERENCES `Address` (`AddressID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_CustomerPrimaryContactID_from_Person`
    FOREIGN KEY (`CustomerPrimaryContactID`)
    REFERENCES `Person` (`PersonID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Invoice`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Invoice` ;

CREATE TABLE IF NOT EXISTS `Invoice` (
  `InvoiceID` INT NOT NULL AUTO_INCREMENT,
  `InvoiceCode` VARCHAR(45) NULL DEFAULT NULL,
  `InvoiceDate` DATE NOT NULL,
  `CustomerID` INT NOT NULL,
  `SalesPersonID` INT NOT NULL,
  PRIMARY KEY (`InvoiceID`, `CustomerID`, `SalesPersonID`),
  INDEX `CustomerID_idx` (`CustomerID` ASC),
  INDEX `PersonID_idx` (`SalesPersonID` ASC),
  CONSTRAINT `fk_CustomerID_from_CustomerToInvoice`
    FOREIGN KEY (`CustomerID`)
    REFERENCES `Customer` (`CustomerID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_SalesPersonID_from_Person`
    FOREIGN KEY (`SalesPersonID`)
    REFERENCES `Person` (`PersonID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Email`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Email` ;

CREATE TABLE IF NOT EXISTS `Email` (
  `EmailID` INT(11) NOT NULL AUTO_INCREMENT,
  `EmailAddress` VARCHAR(45) NOT NULL,
  `PersonID` INT(11) NOT NULL,
  PRIMARY KEY (`EmailID`, `PersonID`),
  INDEX `PersonID_idx` (`PersonID` ASC),
  UNIQUE INDEX `Email_UNIQUE` (`EmailAddress` ASC),
  CONSTRAINT `fk_PersonID_from_PersonToEmail`
    FOREIGN KEY (`PersonID`)
    REFERENCES `Person` (`PersonID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Product`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Product` ;

CREATE TABLE IF NOT EXISTS `Product` (
  `ProductID` INT NOT NULL AUTO_INCREMENT,
  `ProductCode` VARCHAR(45) NULL DEFAULT NULL,
  `ProductType` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`ProductID`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `MovieTicket`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `MovieTicket` ;

CREATE TABLE IF NOT EXISTS `MovieTicket` (
  `MovieTicketID` INT NOT NULL AUTO_INCREMENT,
  `MovieDateTime` DATETIME NOT NULL,
  `MovieName` VARCHAR(45) NOT NULL,
  `ScreenNo` VARCHAR(45) NOT NULL,
  `MovieTicketPrice` DOUBLE NOT NULL,
  `AddressID` INT NOT NULL,
  `ProductID` INT NOT NULL,
  PRIMARY KEY (`MovieTicketID`, `ProductID`),
  INDEX `ProductID_idx` (`ProductID` ASC),
  INDEX `Address_idx` (`AddressID` ASC),
  CONSTRAINT `fk_Address_to_Movie`
    FOREIGN KEY (`AddressID`)
    REFERENCES `Address` (`AddressID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_ProductID_to_Movie`
    FOREIGN KEY (`ProductID`)
    REFERENCES `Product` (`ProductID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Refreshment`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `Refreshment` ;

CREATE TABLE IF NOT EXISTS `Refreshment` (
  `RefreshmentID` INT NOT NULL AUTO_INCREMENT,
  `RefreshmentName` VARCHAR(45) NOT NULL,
  `RefreshmentPrice` DOUBLE NOT NULL,
  `ProductID` INT NOT NULL,
  PRIMARY KEY (`RefreshmentID`, `ProductID`),
  INDEX `fk_Refreshment_Product1_idx` (`ProductID` ASC),
  CONSTRAINT `fk_ProductID_to_Refreshment_fromProduct_from_Product`
    FOREIGN KEY (`ProductID`)
    REFERENCES `Product` (`ProductID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `SeasonPass`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `SeasonPass` ;

CREATE TABLE IF NOT EXISTS `SeasonPass` (
  `SeasonPassID` INT NOT NULL AUTO_INCREMENT,
  `SeasonPassName` VARCHAR(45) NULL DEFAULT NULL,
  `StartDate` DATE NOT NULL,
  `EndDate` DATE NOT NULL,
  `SeasonPassPrice` DOUBLE NOT NULL,
  `ProductID` INT NOT NULL,
  PRIMARY KEY (`SeasonPassID`, `ProductID`),
  INDEX `fk_SeasonPass_Product1_idx` (`ProductID` ASC),
  CONSTRAINT `fk_ProductID_to_SeasonPass_fromProduct`
    FOREIGN KEY (`ProductID`)
    REFERENCES `Product` (`ProductID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `ParkingPass`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `ParkingPass` ;

CREATE TABLE IF NOT EXISTS `ParkingPass` (
  `ParkingPassID` INT NOT NULL AUTO_INCREMENT,
  `ParkingPassPrice` DOUBLE NOT NULL,
  `ProductID` INT NOT NULL,
  PRIMARY KEY (`ParkingPassID`, `ProductID`),
  INDEX `ProductID_idx` (`ProductID` ASC),
  CONSTRAINT `fk_ProductID_ParkingPass_fromProduct`
    FOREIGN KEY (`ProductID`)
    REFERENCES `Product` (`ProductID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `InvoiceProducts`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `InvoiceProducts` ;

CREATE TABLE IF NOT EXISTS `InvoiceProducts` (
  `InvoiceProductsID` INT NOT NULL AUTO_INCREMENT,
  `NumberOfProducts` INT NOT NULL,
  `ProductReference` VARCHAR(45) NULL DEFAULT NULL,
  `InvoiceID` INT NOT NULL,
  `ProductID` INT NOT NULL,
  PRIMARY KEY (`InvoiceProductsID`, `InvoiceID`, `ProductID`),
  INDEX `ProductID_idx` (`ProductID` ASC),
  INDEX `InvoiceID_idx` (`InvoiceID` ASC),
  CONSTRAINT `fk_ProductID_from_ProductToInvoice`
    FOREIGN KEY (`ProductID`)
    REFERENCES `Product` (`ProductID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_InvoiceID_from_InvoiceToInvoiceProduct`
    FOREIGN KEY (`InvoiceID`)
    REFERENCES `Invoice` (`InvoiceID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Data for table `Address`
-- -----------------------------------------------------
START TRANSACTION;

INSERT INTO `Address` (`AddressID`, `Street`, `City`, `State`, `Zip`, `Country`) VALUES (1, ' 123 6th St.', '  Melbourne', '  FL', '  32904', ' USA ');
INSERT INTO `Address` (`AddressID`, `Street`, `City`, `State`, `Zip`, `Country`) VALUES (2, ' 71 Pilgrim Avenue', '  Chevey Chase', '  MD', '  20815', ' USA ');
INSERT INTO `Address` (`AddressID`, `Street`, `City`, `State`, `Zip`, `Country`) VALUES (3, ' 70 Bowman St', '  South Windsor', '  CT', '  06074', ' USA ');
INSERT INTO `Address` (`AddressID`, `Street`, `City`, `State`, `Zip`, `Country`) VALUES (4, ' 1600 Pennslyvania Ave', ' Ottawa', ' ON', ' M5V 2T6', ' Canada ');
INSERT INTO `Address` (`AddressID`, `Street`, `City`, `State`, `Zip`, `Country`) VALUES (5, ' 4 Goldfield Road', '  Honolulu', '  HI', '  96815', ' USA ');
INSERT INTO `Address` (`AddressID`, `Street`, `City`, `State`, `Zip`, `Country`) VALUES (6, ' 44 Shirley Avenue', '  West Chicago', '  IL', '  60185', ' USA ');
INSERT INTO `Address` (`AddressID`, `Street`, `City`, `State`, `Zip`, `Country`) VALUES (7, ' 514 S. Magnolia Street', '  Orlando', '  FL', '  32806', ' USA ');
INSERT INTO `Address` (`AddressID`, `Street`, `City`, `State`, `Zip`, `Country`) VALUES (8, ' 1 Westminster Road', '  Latrobe', '  PA', '  15650', '  USA ');
INSERT INTO `Address` (`AddressID`, `Street`, `City`, `State`, `Zip`, `Country`) VALUES (9, '  494 Creepy Guy Way', '  Hogwarts', '  NE', '  68504', '  USA ');
INSERT INTO `Address` (`AddressID`, `Street`, `City`, `State`, `Zip`, `Country`) VALUES (10, ' 118 Border Dr.', '  Murfreesboro', '  TN', '  37128', '  USA ');
INSERT INTO `Address` (`AddressID`, `Street`, `City`, `State`, `Zip`, `Country`) VALUES (11, ' 8440 Studebaker Drive', '  Portage', '  IN', '  46368', ' USA ');
INSERT INTO `Address` (`AddressID`, `Street`, `City`, `State`, `Zip`, `Country`) VALUES (12, ' 9596 Country Ave.', '  Kind of Prussia', '  PA', '  19406', ' USA ');
INSERT INTO `Address` (`AddressID`, `Street`, `City`, `State`, `Zip`, `Country`) VALUES (13, ' 39 Mill Drive', '  Casselberry', '  FL', '  32707', ' USA ');
INSERT INTO `Address` (`AddressID`, `Street`, `City`, `State`, `Zip`, `Country`) VALUES (14, ' 12 Grimmauld Place', '  UnderTheStairs', '  NY', ' 10451', ' USA ');
INSERT INTO `Address` (`AddressID`, `Street`, `City`, `State`, `Zip`, `Country`) VALUES (15, ' 9045 Arrowhead Street', '  Rapid City', '  SD', '  57701', ' USA');
INSERT INTO `Address` (`AddressID`, `Street`, `City`, `State`, `Zip`, `Country`) VALUES (16, ' 778 Avery Hall', ' Lincoln', ' NE', ' 68588-0115', ' USA ');
INSERT INTO `Address` (`AddressID`, `Street`, `City`, `State`, `Zip`, `Country`) VALUES (17, ' 6528 East St', ' Gothum', ' NE', ' 68545', ' USA ');
INSERT INTO `Address` (`AddressID`, `Street`, `City`, `State`, `Zip`, `Country`) VALUES (18, ' 48632 Boulevard', ' Metropolis', ' CA', ' 95530', ' USA ');
INSERT INTO `Address` (`AddressID`, `Street`, `City`, `State`, `Zip`, `Country`) VALUES (19, ' 1563 Fireball Dropper Ave', ' Star City', ' MD', ' 25065', ' USA ');
INSERT INTO `Address` (`AddressID`, `Street`, `City`, `State`, `Zip`, `Country`) VALUES (20, ' 0025 Double North', ' Central City', ' IL', ' 60601', ' USA ');
INSERT INTO `Address` (`AddressID`, `Street`, `City`, `State`, `Zip`, `Country`) VALUES (21, ' 819 White House Rd.', ' Chicago', ' NE', ' 68450', ' USA ');
INSERT INTO `Address` (`AddressID`, `Street`, `City`, `State`, `Zip`, `Country`) VALUES (22, ' 1600 Pennsylvania', ' Couch', ' NE', ' 68777', ' USA ');
INSERT INTO `Address` (`AddressID`, `Street`, `City`, `State`, `Zip`, `Country`) VALUES (23, ' 7 Street', ' Cardiff', ' WA', ' 61860', ' USA');
INSERT INTO `Address` (`AddressID`, `Street`, `City`, `State`, `Zip`, `Country`) VALUES (24, ' 1286 Wonder City Plaza', ' Gotham', '  AR', '  68144', '  USA ');
INSERT INTO `Address` (`AddressID`, `Street`, `City`, `State`, `Zip`, `Country`) VALUES (25, ' 755 Wilson Drive', ' Cair Paravel', '  KY', '  11110', '  USA ');
INSERT INTO `Address` (`AddressID`, `Street`, `City`, `State`, `Zip`, `Country`) VALUES (26, ' 1036 Black Gate', ' Mordor', '  IL', '  31605', '  USA');

COMMIT;


-- -----------------------------------------------------
-- Data for table `Person`
-- -----------------------------------------------------
START TRANSACTION;

INSERT INTO `Person` (`PersonID`, `PersonCode`, `PersonFirstName`, `PersonLastName`, `AddressID`) VALUES (1, ' 456d', ' Godric', ' Gryffindor',  1);
INSERT INTO `Person` (`PersonID`, `PersonCode`, `PersonFirstName`, `PersonLastName`, `AddressID`) VALUES (2, ' 306a', ' Gellert', ' Grindelwald',  2);
INSERT INTO `Person` (`PersonID`, `PersonCode`, `PersonFirstName`, `PersonLastName`, `AddressID`) VALUES (3, ' 55bb', ' Hermione', ' Granger',  3);
INSERT INTO `Person` (`PersonID`, `PersonCode`, `PersonFirstName`, `PersonLastName`, `AddressID`) VALUES (4, ' 8963', ' Lily', ' Potter',  4);
INSERT INTO `Person` (`PersonID`, `PersonCode`, `PersonFirstName`, `PersonLastName`, `AddressID`) VALUES (5, ' mad12', ' Helga', ' Hufflepuff',  5);
INSERT INTO `Person` (`PersonID`, `PersonCode`, `PersonFirstName`, `PersonLastName`, `AddressID`) VALUES (6, ' 321nd', ' Hannah', ' Abbot',  6);
INSERT INTO `Person` (`PersonID`, `PersonCode`, `PersonFirstName`, `PersonLastName`, `AddressID`) VALUES (7, ' nf32a', ' Sirius', ' Black',  7);
INSERT INTO `Person` (`PersonID`, `PersonCode`, `PersonFirstName`, `PersonLastName`, `AddressID`) VALUES (8, ' 321na', ' Cho', ' Chang',  8);
INSERT INTO `Person` (`PersonID`, `PersonCode`, `PersonFirstName`, `PersonLastName`, `AddressID`) VALUES (9, ' 231f', ' Tom', ' Riddle',  9);
INSERT INTO `Person` (`PersonID`, `PersonCode`, `PersonFirstName`, `PersonLastName`, `AddressID`) VALUES (10, ' 6doc', ' Lavender', ' Brown',  10);
INSERT INTO `Person` (`PersonID`, `PersonCode`, `PersonFirstName`, `PersonLastName`, `AddressID`) VALUES (11, ' 321dr', ' Bartemius', ' Crouch',  11);
INSERT INTO `Person` (`PersonID`, `PersonCode`, `PersonFirstName`, `PersonLastName`, `AddressID`) VALUES (12, ' 1svndr', ' Alastor', ' Moody',  12);
INSERT INTO `Person` (`PersonID`, `PersonCode`, `PersonFirstName`, `PersonLastName`, `AddressID`) VALUES (13, ' 123lst', ' Albus', ' Dumbledore',  13);
INSERT INTO `Person` (`PersonID`, `PersonCode`, `PersonFirstName`, `PersonLastName`, `AddressID`) VALUES (14, ' nwdo1', ' Vernon', ' Dursley',  14);
INSERT INTO `Person` (`PersonID`, `PersonCode`, `PersonFirstName`, `PersonLastName`, `AddressID`) VALUES (15, ' 2ndbest', ' Cornelius', ' Fudge',  15);

COMMIT;


-- -----------------------------------------------------
-- Data for table `Customer`
-- -----------------------------------------------------
START TRANSACTION;

INSERT INTO `Customer` (`CustomerID`, `CustomerCode`, `CustomerName`, `CustomerType`, `CustomerPrimaryContactID`, `CustomerAddressID`) VALUES (1, ' C001', ' Beirut International', ' G',  16,  10 );
INSERT INTO `Customer` (`CustomerID`, `CustomerCode`, `CustomerName`, `CustomerType`, `CustomerPrimaryContactID`, `CustomerAddressID`) VALUES (2, ' C002', ' Super Saver', ' S',  17,  15 );
INSERT INTO `Customer` (`CustomerID`, `CustomerCode`, `CustomerName`, `CustomerType`, `CustomerPrimaryContactID`, `CustomerAddressID`) VALUES (3, ' C003', ' Lex corp', ' S',  18,  2 );
INSERT INTO `Customer` (`CustomerID`, `CustomerCode`, `CustomerName`, `CustomerType`, `CustomerPrimaryContactID`, `CustomerAddressID`) VALUES (4, ' C004', ' Apple', ' G',  19,  3 );
INSERT INTO `Customer` (`CustomerID`, `CustomerCode`, `CustomerName`, `CustomerType`, `CustomerPrimaryContactID`, `CustomerAddressID`) VALUES (5, ' C005', ' Queen Industries', ' G',  20,  12 );
INSERT INTO `Customer` (`CustomerID`, `CustomerCode`, `CustomerName`, `CustomerType`, `CustomerPrimaryContactID`, `CustomerAddressID`) VALUES (6, ' C006', ' Wayne Tech', ' S',  21,  11 );
INSERT INTO `Customer` (`CustomerID`, `CustomerCode`, `CustomerName`, `CustomerType`, `CustomerPrimaryContactID`, `CustomerAddressID`) VALUES (7, ' C007', ' Hamilton', ' G',  22,  9 );
INSERT INTO `Customer` (`CustomerID`, `CustomerCode`, `CustomerName`, `CustomerType`, `CustomerPrimaryContactID`, `CustomerAddressID`) VALUES (8, ' C008', ' Hammer Tech', ' S',  23,  15);

INSERT INTO `Customer` (`CustomerID`, `CustomerCode`, `CustomerName`, `CustomerType`, `CustomerPrimaryContactID`, `CustomerAddressID`) VALUES (9, ' C009', ' Hammersdf Tech', ' S',  15,  23); 
-- duplicate entry for test case

COMMIT;


-- -----------------------------------------------------
-- Data for table `Invoice`
-- -----------------------------------------------------
START TRANSACTION;

INSERT INTO `Invoice` (`InvoiceID`, `InvoiceCode`, `InvoiceDate`, `CustomerID`, `SalesPersonID`) VALUES (1, ' INV001', ' 2016-08-03',  1,  10 );
INSERT INTO `Invoice` (`InvoiceID`, `InvoiceCode`, `InvoiceDate`, `CustomerID`, `SalesPersonID`) VALUES (2, ' INV002', ' 2017-02-28',  3,  2 );
INSERT INTO `Invoice` (`InvoiceID`, `InvoiceCode`, `InvoiceDate`, `CustomerID`, `SalesPersonID`) VALUES (3, ' INV003', ' 2016-12-29',  5,  12 );
INSERT INTO `Invoice` (`InvoiceID`, `InvoiceCode`, `InvoiceDate`, `CustomerID`, `SalesPersonID`) VALUES (4, ' INV994', ' 2016-06-12',  8,  15);
INSERT INTO `Invoice` (`InvoiceID`, `InvoiceCode`, `InvoiceDate`, `CustomerID`, `SalesPersonID`) VALUES (5, ' INV994d', ' 2016-06-13',  9,  15); 
-- duplicate entry for test case

COMMIT;


-- -----------------------------------------------------
-- Data for table `Email`
-- -----------------------------------------------------
START TRANSACTION;

INSERT INTO `Email` (`EmailID`, `EmailAddress`, `PersonID`) VALUES (1, ' 10points2Gryffindor@gmail.com',  1);
INSERT INTO `Email` (`EmailID`, `EmailAddress`, `PersonID`) VALUES (2, ' the_flash@gmail.com',  2);
INSERT INTO `Email` (`EmailID`, `EmailAddress`, `PersonID`) VALUES (3, ' Lt.Jordan@adventure.com',  2);
INSERT INTO `Email` (`EmailID`, `EmailAddress`, `PersonID`) VALUES (4, ' starTrek@enterprise.gov',  3);
INSERT INTO `Email` (`EmailID`, `EmailAddress`, `PersonID`) VALUES (5, ' DrWhoville@yahoo.com',  4);
INSERT INTO `Email` (`EmailID`, `EmailAddress`, `PersonID`) VALUES (6, ' ferrisBueller@dayOff.edu',  6);
INSERT INTO `Email` (`EmailID`, `EmailAddress`, `PersonID`) VALUES (7, ' brokeKid@avenger.gov',  7);
INSERT INTO `Email` (`EmailID`, `EmailAddress`, `PersonID`) VALUES (8, ' westPhilly@bornAndRaised.com',  8);
INSERT INTO `Email` (`EmailID`, `EmailAddress`, `PersonID`) VALUES (9, ' cyborg@watchtower.com',  9);
INSERT INTO `Email` (`EmailID`, `EmailAddress`, `PersonID`) VALUES (10, '  dprince@metroartmuseum.gov',  9);
INSERT INTO `Email` (`EmailID`, `EmailAddress`, `PersonID`) VALUES (11, '  datsyukfan101@crazy.net',  9);
INSERT INTO `Email` (`EmailID`, `EmailAddress`, `PersonID`) VALUES (12, ' spiderPig@aol.com',  10);
INSERT INTO `Email` (`EmailID`, `EmailAddress`, `PersonID`) VALUES (13, ' whoLikesDrWho@weDo.com',  12);
INSERT INTO `Email` (`EmailID`, `EmailAddress`, `PersonID`) VALUES (14, ' baseball@mlb.com',  13);
INSERT INTO `Email` (`EmailID`, `EmailAddress`, `PersonID`) VALUES (15, '  football@nfl.edu',  13);
INSERT INTO `Email` (`EmailID`, `EmailAddress`, `PersonID`) VALUES (16, ' computersare@coo.com',  14);
INSERT INTO `Email` (`EmailID`, `EmailAddress`, `PersonID`) VALUES (17, ' nonTrivial@testcases.gov',  15);

COMMIT;


-- -----------------------------------------------------
-- Data for table `Product`
-- -----------------------------------------------------
START TRANSACTION;

INSERT INTO `Product` (`ProductID`, `ProductCode`, `ProductType`) VALUES (1, ' b29e', ' S ');
INSERT INTO `Product` (`ProductID`, `ProductCode`, `ProductType`) VALUES (2, ' ff23', ' R ');
INSERT INTO `Product` (`ProductID`, `ProductCode`, `ProductType`) VALUES (3, ' fp12', ' M ');
INSERT INTO `Product` (`ProductID`, `ProductCode`, `ProductType`) VALUES (4, ' 90fa', ' P ');
INSERT INTO `Product` (`ProductID`, `ProductCode`, `ProductType`) VALUES (5, ' 1239', ' M ');
INSERT INTO `Product` (`ProductID`, `ProductCode`, `ProductType`) VALUES (6, ' 782g', ' M ');
INSERT INTO `Product` (`ProductID`, `ProductCode`, `ProductType`) VALUES (7, ' 3289', ' P ');
INSERT INTO `Product` (`ProductID`, `ProductCode`, `ProductType`) VALUES (8, ' 32f4', ' R ');
INSERT INTO `Product` (`ProductID`, `ProductCode`, `ProductType`) VALUES (9, ' 3y92', ' S ');
INSERT INTO `Product` (`ProductID`, `ProductCode`, `ProductType`) VALUES (10, ' 90fb', ' P ');
INSERT INTO `Product` (`ProductID`, `ProductCode`, `ProductType`) VALUES (11, ' xer4', ' S ');
INSERT INTO `Product` (`ProductID`, `ProductCode`, `ProductType`) VALUES (12, ' yp23', ' R ');
INSERT INTO `Product` (`ProductID`, `ProductCode`, `ProductType`) VALUES (13, ' sdyu', ' S');

COMMIT;


-- -----------------------------------------------------
-- Data for table `MovieTicket`
-- -----------------------------------------------------
START TRANSACTION;

INSERT INTO `MovieTicket` (`MovieTicketID`, `MovieDateTime`, `MovieName`, `ScreenNo`, `MovieTicketPrice`, `AddressID`, `ProductID`) VALUES (1, ' 2016-10-21 13:10:00', ' Enough Pizza', ' 12A',  11.5,  24,  3 );
INSERT INTO `MovieTicket` (`MovieTicketID`, `MovieDateTime`, `MovieName`, `ScreenNo`, `MovieTicketPrice`, `AddressID`, `ProductID`) VALUES (2, ' 2017-05-03 18:40:00', ' Infinity Gauntlet', ' 12G',  18.0,  25,  5 );
INSERT INTO `MovieTicket` (`MovieTicketID`, `MovieDateTime`, `MovieName`, `ScreenNo`, `MovieTicketPrice`, `AddressID`, `ProductID`) VALUES (3, ' 2016-12-10 10:50:00', ' Ring of Power', ' 30D',  15.0,  26,  6);

COMMIT;


-- -----------------------------------------------------
-- Data for table `Refreshment`
-- -----------------------------------------------------
START TRANSACTION;

INSERT INTO `Refreshment` (`RefreshmentID`, `RefreshmentName`, `RefreshmentPrice`, `ProductID`) VALUES (1, ' tasty tasty tasty',  4.99,  2 );
INSERT INTO `Refreshment` (`RefreshmentID`, `RefreshmentName`, `RefreshmentPrice`, `ProductID`) VALUES (2, ' Cheddar Popcorn with M&Ms',  5.5,  8 );
INSERT INTO `Refreshment` (`RefreshmentID`, `RefreshmentName`, `RefreshmentPrice`, `ProductID`) VALUES (3, ' Chicken Tenders',  7.0,  12);

COMMIT;


-- -----------------------------------------------------
-- Data for table `SeasonPass`
-- -----------------------------------------------------
START TRANSACTION;

INSERT INTO `SeasonPass` (`SeasonPassID`, `SeasonPassName`, `StartDate`, `EndDate`, `SeasonPassPrice`, `ProductID`) VALUES (1, ' Spring Special', ' 2016-03-13', ' 2016-05-07',  150.0,  1 );
INSERT INTO `SeasonPass` (`SeasonPassID`, `SeasonPassName`, `StartDate`, `EndDate`, `SeasonPassPrice`, `ProductID`) VALUES (2, ' Christmas Special', ' 2016-12-24', ' 2017-02-28',  205.0,  9 );
INSERT INTO `SeasonPass` (`SeasonPassID`, `SeasonPassName`, `StartDate`, `EndDate`, `SeasonPassPrice`, `ProductID`) VALUES (3, ' TwoWeeks-Unlimited', ' 2016-10-07', ' 2016-10-21',  75.0,  11 );
INSERT INTO `SeasonPass` (`SeasonPassID`, `SeasonPassName`, `StartDate`, `EndDate`, `SeasonPassPrice`, `ProductID`) VALUES (4, ' Good Deal', ' 2011-11-11', ' 2018-11-11',  999.0,  14);

COMMIT;


-- -----------------------------------------------------
-- Data for table `ParkingPass`
-- -----------------------------------------------------
START TRANSACTION;

INSERT INTO `ParkingPass` (`ParkingPassID`, `ParkingPassPrice`, `ProductID`) VALUES (1,  20.0,  4);
INSERT INTO `ParkingPass` (`ParkingPassID`, `ParkingPassPrice`, `ProductID`) VALUES (2,  75.0,  7);
INSERT INTO `ParkingPass` (`ParkingPassID`, `ParkingPassPrice`, `ProductID`) VALUES (3,  30.0,  10);

COMMIT;


-- -----------------------------------------------------
-- Data for table `InvoiceProducts`
-- -----------------------------------------------------
START TRANSACTION;

INSERT INTO `InvoiceProducts` (`InvoiceProductsID`, `NumberOfProducts`, `ProductReference`, `InvoiceID`, `ProductID`) VALUES (1, 2, NULL, 1, 5);

INSERT INTO `InvoiceProducts` (`InvoiceProductsID`, `NumberOfProducts`, `ProductReference`, `InvoiceID`, `ProductID`) VALUES (15, 2, NULL, 1, 5);
-- duplicate entry for test case

INSERT INTO `InvoiceProducts` (`InvoiceProductsID`, `NumberOfProducts`, `ProductReference`, `InvoiceID`, `ProductID`) VALUES (18, 2, NULL, 1, 6);
-- duplicate entry for test case
INSERT INTO `InvoiceProducts` (`InvoiceProductsID`, `NumberOfProducts`, `ProductReference`, `InvoiceID`, `ProductID`) VALUES (2, 1, '1239', 1, 10);
INSERT INTO `InvoiceProducts` (`InvoiceProductsID`, `NumberOfProducts`, `ProductReference`, `InvoiceID`, `ProductID`) VALUES (3, 3, NULL, 1, 12);
INSERT INTO `InvoiceProducts` (`InvoiceProductsID`, `NumberOfProducts`, `ProductReference`, `InvoiceID`, `ProductID`) VALUES (4, 23, NULL, 2, 9);
INSERT INTO `InvoiceProducts` (`InvoiceProductsID`, `NumberOfProducts`, `ProductReference`, `InvoiceID`, `ProductID`) VALUES (5, 31, NULL, 2, 5);
INSERT INTO `InvoiceProducts` (`InvoiceProductsID`, `NumberOfProducts`, `ProductReference`, `InvoiceID`, `ProductID`) VALUES (6, 30, '3y94', 2, 4);
INSERT INTO `InvoiceProducts` (`InvoiceProductsID`, `NumberOfProducts`, `ProductReference`, `InvoiceID`, `ProductID`) VALUES (7, 2, NULL, 2, 8);
INSERT INTO `InvoiceProducts` (`InvoiceProductsID`, `NumberOfProducts`, `ProductReference`, `InvoiceID`, `ProductID`) VALUES (16, 2, NULL, 2, 8);
-- duplicate entry for test case
INSERT INTO `InvoiceProducts` (`InvoiceProductsID`, `NumberOfProducts`, `ProductReference`, `InvoiceID`, `ProductID`) VALUES (8, 7, NULL, 3, 6);
INSERT INTO `InvoiceProducts` (`InvoiceProductsID`, `NumberOfProducts`, `ProductReference`, `InvoiceID`, `ProductID`) VALUES (17, 7, NULL, 3, 6);
-- duplicate entry for test case

INSERT INTO `InvoiceProducts` (`InvoiceProductsID`, `NumberOfProducts`, `ProductReference`, `InvoiceID`, `ProductID`) VALUES (19, 7, NULL, 3, 6);
-- duplicate entry for test case

INSERT INTO `InvoiceProducts` (`InvoiceProductsID`, `NumberOfProducts`, `ProductReference`, `InvoiceID`, `ProductID`) VALUES (20, 7, NULL, 3, 5);
-- duplicate entry for test case

INSERT INTO `InvoiceProducts` (`InvoiceProductsID`, `NumberOfProducts`, `ProductReference`, `InvoiceID`, `ProductID`) VALUES (21, 7, NULL, 3, 5);
-- duplicate entry for test case
INSERT INTO `InvoiceProducts` (`InvoiceProductsID`, `NumberOfProducts`, `ProductReference`, `InvoiceID`, `ProductID`) VALUES (9, 6, NULL, 3, 9);
INSERT INTO `InvoiceProducts` (`InvoiceProductsID`, `NumberOfProducts`, `ProductReference`, `InvoiceID`, `ProductID`) VALUES (10, 2, '782g', 3, 7);
INSERT INTO `InvoiceProducts` (`InvoiceProductsID`, `NumberOfProducts`, `ProductReference`, `InvoiceID`, `ProductID`) VALUES (11, 4, NULL, 3, 12);
INSERT INTO `InvoiceProducts` (`InvoiceProductsID`, `NumberOfProducts`, `ProductReference`, `InvoiceID`, `ProductID`) VALUES (12, 3, NULL, 4, 4);
INSERT INTO `InvoiceProducts` (`InvoiceProductsID`, `NumberOfProducts`, `ProductReference`, `InvoiceID`, `ProductID`) VALUES (13, 3, NULL, 4, 8);
INSERT INTO `InvoiceProducts` (`InvoiceProductsID`, `NumberOfProducts`, `ProductReference`, `InvoiceID`, `ProductID`) VALUES (14, 31, NULL, 4, 13);

COMMIT;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;