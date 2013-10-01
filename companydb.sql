DROP DATABASE IF EXISTS companydb;
CREATE DATABASE companydb;
USE companydb; 

DROP TABLE IF EXISTS company;
CREATE TABLE company
(dept_ID INT,
 dept_Name VARCHAR(30),
 PRIMARY KEY(dept_ID)
);

DROP TABLE IF EXISTS product;
CREATE TABLE product
(pID INT,
 pNAME VARCHAR(30),
 PRIMARY KEY (pID)
); 

DROP TABLE IF EXISTS product_list;
CREATE TABLE product_list
(pID INT,
 pCode INT,
 pTitle VARCHAR (50),
 pPrice DOUBLE,
 PRIMARY KEY(pID,pCode,pTitle)
); 

LOAD DATA LOCAL INFILE 'C:\\Project_49J\\JDBC_Project\\src\\company.txt' INTO TABLE company;
LOAD DATA LOCAL INFILE 'C:\\Project_49J\\JDBC_Project\\src\\product.txt' INTO TABLE product;
LOAD DATA LOCAL INFILE 'C:\\Project_49J\\JDBC_Project\\src\\product_list.txt' INTO TABLE product_list;
