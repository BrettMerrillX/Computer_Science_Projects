CREATE SCHEMA IF NOT EXISTS `PARTS_MOD` DEFAULT CHARACTER SET latin1 ;
USE `PARTS_MOD` ;

CREATE Table PARTS_MOD.Customer (
 `cus_ID` int NOT NULL AUTO_INCREMENT,
 `name` varchar(255),
 `city` varchar(255),
 `scode` varchar(255),
 `phone` bigint(10) NOT NULL DEFAULT '0',
 PRIMARY KEY (`cus_ID`),
 FOREIGN KEY (`scode`) REFERENCES `PARTS_MOD`.`State` (`scode`)
 ) AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

CREATE Table PARTS_MOD.Product (
 `prod_ID` int NOT NULL AUTO_INCREMENT,
 `name` varchar(255),
 `developer_ID` varchar(255),
 `vst_ID` varchar(255),
 `price` varchar(255),
 PRIMARY KEY (`prod_ID`),
 FOREIGN KEY (`developer_ID`) REFERENCES `PARTS_MOD`.`Developer` (`developer_ID`),
 FOREIGN KEY (`vst_ID`) REFERENCES `PARTS_MOD`.`VSTs` (`vst_ID`)
 ) AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;
 
 CREATE Table PARTS_MOD.License (
 `license_num` varchar(255),
 `prod_ID` varchar(255),
 PRIMARY KEY (`license_num`),
 FOREIGN KEY (`prod_ID`) REFERENCES `PARTS_MOD`.`Product` (`prod_ID`)
 ) AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;
 
  CREATE Table PARTS_MOD.Orders (
 `order_num` int NOT NULL AUTO_INCREMENT,
 `cus_ID` varchar(255),
 `prod_ID` varchar(255),
 `license_num` varchar(255),
 PRIMARY KEY (`order_num`),
 FOREIGN KEY (`cus_ID`) REFERENCES `PARTS_MOD`.`Customer` (`cus_ID`),
 FOREIGN KEY (`license_num`) REFERENCES `PARTS_MOD`.`License` (`license_num`),
 FOREIGN KEY (`prod_ID`) REFERENCES `PARTS_MOD`.`Product` (`prod_ID`)
 ) AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;
 
   CREATE Table PARTS_MOD.State (
 `scode` varchar(255),
 `sname` varchar(255),
 PRIMARY KEY (`scode`)
 ) AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;
 
CREATE Table PARTS_MOD.Popular_musicians (
 `cus_ID` varchar(255),
 `prod_ID` varchar(255),
 `name` varchar(255),
 FOREIGN KEY (`cus_ID`) REFERENCES `PARTS_MOD`.`Customer` (`cus_ID`)
 ) AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;
 
 CREATE Table PARTS_MOD.Compatability (
 `daw_ID` int NOT NULL AUTO_INCREMENT,
 `prod_ID` varchar(255),
 PRIMARY KEY (`daw_ID`)
 ) AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;
 
 CREATE Table PARTS_MOD.VSTs (
 `vst_ID` int NOT NULL AUTO_INCREMENT,
 `vst_type` varchar(255),
 PRIMARY KEY (`vst_ID`)
 ) AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;
 
  CREATE Table PARTS_MOD.Developer (
 `developer_ID` int NOT NULL AUTO_INCREMENT,
 `name` varchar(255),
 PRIMARY KEY (`developer_ID`)
 ) AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;
 
 

