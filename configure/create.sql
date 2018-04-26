CREATE DATABASE `world` /*!40100 DEFAULT CHARACTER SET utf8 */;

DROP TABLE IF EXISTS city;
CREATE TABLE `city` (
  `ID` int(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
  `Name` char(35) NOT NULL DEFAULT '',
  `CountryCode` char(10) NOT NULL DEFAULT '',
  `District` char(20) NOT NULL DEFAULT '',
  `Population` int(11) NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci COMMENT 'city';


/*
-- Query: SELECT * FROM world.city
LIMIT 0, 1000

-- Date: 2017-11-05 23:55
*/
INSERT INTO `city` (`ID`,`Name`,`CountryCode`,`District`,`Population`) VALUES (1,'Kabul','AFG','Kabol',1780000);
INSERT INTO `city` (`ID`,`Name`,`CountryCode`,`District`,`Population`) VALUES (2,'Qandahar','AFG','Qandahar',237500);
INSERT INTO `city` (`ID`,`Name`,`CountryCode`,`District`,`Population`) VALUES (3,'Herat','AFG','Herat',186800);
INSERT INTO `city` (`ID`,`Name`,`CountryCode`,`District`,`Population`) VALUES (4,'Mazar-e-Sharif','AFG','Balkh',127800);
INSERT INTO `city` (`ID`,`Name`,`CountryCode`,`District`,`Population`) VALUES (5,'Amsterdam','NLD','Noord-Holland',731200);
INSERT INTO `city` (`ID`,`Name`,`CountryCode`,`District`,`Population`) VALUES (6,'Rotterdam','NLD','Zuid-Holland',593321);
INSERT INTO `city` (`ID`,`Name`,`CountryCode`,`District`,`Population`) VALUES (7,'Haag','NLD','Zuid-Holland',440900);
INSERT INTO `city` (`ID`,`Name`,`CountryCode`,`District`,`Population`) VALUES (8,'Utrecht','NLD','Utrecht',234323);
INSERT INTO `city` (`ID`,`Name`,`CountryCode`,`District`,`Population`) VALUES (9,'Eindhoven','NLD','Noord-Brabant',201843);
INSERT INTO `city` (`ID`,`Name`,`CountryCode`,`District`,`Population`) VALUES (10,'Tilburg','NLD','Noord-Brabant',193238);
INSERT INTO `city` (`ID`,`Name`,`CountryCode`,`District`,`Population`) VALUES (11,'Groningen','NLD','Groningen',172701);
INSERT INTO `city` (`ID`,`Name`,`CountryCode`,`District`,`Population`) VALUES (12,'Breda','NLD','Noord-Brabant',160398);
INSERT INTO `city` (`ID`,`Name`,`CountryCode`,`District`,`Population`) VALUES (13,'Apeldoorn','NLD','Gelderland',153491);
INSERT INTO `city` (`ID`,`Name`,`CountryCode`,`District`,`Population`) VALUES (14,'Nijmegen','NLD','Gelderland',152463);
INSERT INTO `city` (`ID`,`Name`,`CountryCode`,`District`,`Population`) VALUES (15,'Enschede','NLD','Overijssel',149544);
