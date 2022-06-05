create database atmmachin;

use AtmMachin;
CREATE TABLE IF NOT EXISTS `customer` (
  `customerId` bigint NOT NULL AUTO_INCREMENT,
  `accountNumber` bigint DEFAULT NULL,
  `pin` bigint DEFAULT NULL,
  `balanceAmount` bigint DEFAULT NULL,
  `overDraftAmount` bigint DEFAULT NULL,
  `created_at` datetime NULL,
  PRIMARY KEY (`customerId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
INSERT INTO customer (accountNumber, pin, balanceAmount, overdraftAmount) 
VALUES('123456789', 1234, 800, 200);
INSERT INTO customer (accountNumber, pin, balanceAmount, overdraftAmount) 
VALUES('987654321', 4321, 1230, 150);

CREATE TABLE IF NOT EXISTS `denomination` (
  `denominatioId` bigint NOT NULL AUTO_INCREMENT,
  `noteValue` bigint DEFAULT NULL,
  `noteQuantity` bigint DEFAULT NULL,
  PRIMARY KEY (`denominatioId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
INSERT INTO denomination (noteValue, noteQuantity) 
VALUES(50,20);
INSERT INTO denomination (noteValue, noteQuantity) 
VALUES(20,30);
INSERT INTO denomination (noteValue, noteQuantity) 
VALUES(10,30);
INSERT INTO denomination (noteValue, noteQuantity) 
VALUES(5,20);