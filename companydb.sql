/* Schema: companydb */
DROP DATABASE IF EXISTS companydb;
CREATE DATABASE companydb;
USE companydb; 


/* Table 1: company */
DROP TABLE IF EXISTS company;
CREATE TABLE company
(dept_ID INT,
 dept_Name VARCHAR (50),
 dept_Manager VARCHAR (100),
 PRIMARY KEY(dept_ID)
);


/* Table 2: employee */
DROP TABLE IF EXISTS employee;
CREATE TABLE employee
(dept_ID INT,
 empl_ID INT,
 empl_Name VARCHAR (100),
 empl_DOB DATE, 
 empl_Email VARCHAR (100), 
 empl_Position VARCHAR (50), 
 empl_Salary DECIMAL(10,2),
 PRIMARY KEY(dept_ID, empl_ID, empl_Name)
);


/* Table 3: transactions */
DROP TABLE IF EXISTS transactions;
CREATE TABLE transactions
(empl_ID INT, 
 prod_No INT, 
 sale_Name VARCHAR(30), 
 sale_Year INT, 
 sale_Case INT,
 sale_Amount DECIMAL(10,2),
 PRIMARY KEY(prod_No)
);


/* Table 4: product */
DROP TABLE IF EXISTS product;
CREATE TABLE product
(prod_Code VARCHAR(10) NOT NULL,
 prod_Name VARCHAR(30) NOT NULL,
 UNIQUE (prod_Code)
); 


/* Table 5: inventory */
DROP TABLE IF EXISTS inventory;
CREATE TABLE inventory
(prod_Code VARCHAR(10) NOT NULL,
 prod_No INT NOT NULL,
 prod_Title VARCHAR (200),
 prod_Case_Qty INT,
 prod_Case_Weight INT,
 prod_Case_Price DECIMAL(10,2),
 PRIMARY KEY(prod_Code, prod_No, prod_Title)
); 


/* Table 6: customer */
DROP TABLE IF EXISTS customer;
CREATE TABLE customer
(cust_ID INT NOT NULL AUTO_INCREMENT,
 cust_Name VARCHAR (100),
 cust_Email VARCHAR (100),
 cust_Date DATE,
 PRIMARY KEY(cust_ID)
);


/* Table 7: orders */
DROP TABLE IF EXISTS orders;
CREATE TABLE orders
(ord_ID INT NOT NULL AUTO_INCREMENT,
 sale_Name CHAR(100),
 ord_Date DATE,
 cust_Name CHAR(100),
 prod_No INT,
 prod_Title CHAR(200),
 ord_Case INT,
 ord_Amount DECIMAL(10,2),
 PRIMARY KEY(ord_ID)
);


LOAD DATA LOCAL INFILE 'C:\\Project_49J\\JDBC_Project\\src\\company.txt' INTO TABLE company;
LOAD DATA LOCAL INFILE 'C:\\Project_49J\\JDBC_Project\\src\\employee.txt' INTO TABLE employee;
LOAD DATA LOCAL INFILE 'C:\\Project_49J\\JDBC_Project\\src\\transactions.txt' INTO TABLE transactions;
LOAD DATA LOCAL INFILE 'C:\\Project_49J\\JDBC_Project\\src\\product.txt' INTO TABLE product;
LOAD DATA LOCAL INFILE 'C:\\Project_49J\\JDBC_Project\\src\\inventory.txt' INTO TABLE inventory;
LOAD DATA LOCAL INFILE 'C:\\Project_49J\\JDBC_Project\\src\\customer.txt' INTO TABLE customer;
LOAD DATA LOCAL INFILE 'C:\\Project_49J\\JDBC_Project\\src\\orders.txt' INTO TABLE orders;


