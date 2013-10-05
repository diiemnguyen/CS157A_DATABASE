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
 empl_Salary DOUBLE,
 PRIMARY KEY(dept_ID, empl_ID, empl_Name)
);


/* Table 3: transactions */
DROP TABLE IF EXISTS transactions;
CREATE TABLE transactions
(empl_ID INT, 
<<<<<<< HEAD
 prod_ID VARCHAR(10), 
 cust_ID INT, 
 sale_case INT, 
 sale_amount DOUBLE,
 PRIMARY KEY(empl_ID,prod_ID)
=======
 prod_No INT, 
 sale_Name CHAR(30), 
 sale_Year INT, 
 sale_Case INT,
 sale_Amount DOUBLE,
 PRIMARY KEY(prod_No)
>>>>>>> done sql and 7 tables
);


/* Table 4: product */
DROP TABLE IF EXISTS product;
CREATE TABLE product
<<<<<<< HEAD
(prod_ID VARCHAR(10),
 prod_NAME VARCHAR(30),
 PRIMARY KEY (prod_ID)
=======
(prod_Code VARCHAR(10),
 prod_Name VARCHAR(30),
 PRIMARY KEY (prod_Name)
>>>>>>> done sql and 7 tables
); 


/* Table 5: inventory */
DROP TABLE IF EXISTS inventory;
CREATE TABLE inventory
<<<<<<< HEAD
(prod_ID VARCHAR(10),
=======
(prod_Code CHAR(10),
>>>>>>> done sql and 7 tables
 prod_No INT,
 prod_Title VARCHAR (200),
 prod_Case_Qty INT,
 prod_Case_Weight INT,
 prod_Case_Price DOUBLE,
 PRIMARY KEY(prod_No, prod_Title)
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
<<<<<<< HEAD
(cust_ID INT,
 prod_ID VARCHAR(10),
 cust_Total_Order INT,
 cust_Year_Order INT,
 cust_Total_Amount DOUBLE
=======
(ord_ID INT NOT NULL AUTO_INCREMENT,
 sale_Name CHAR(100),
 ord_Date DATE,
 cust_Name CHAR(100),
 prod_No INT,
 prod_Title CHAR(200),
 ord_Case INT,
 ord_Amount DOUBLE,
 PRIMARY KEY(ord_ID)
>>>>>>> done sql and 7 tables
);


LOAD DATA LOCAL INFILE 'C:\\Project_49J\\JDBC_Project\\src\\company.txt' INTO TABLE company;
LOAD DATA LOCAL INFILE 'C:\\Project_49J\\JDBC_Project\\src\\employee.txt' INTO TABLE employee;
LOAD DATA LOCAL INFILE 'C:\\Project_49J\\JDBC_Project\\src\\transactions.txt' INTO TABLE transactions;
LOAD DATA LOCAL INFILE 'C:\\Project_49J\\JDBC_Project\\src\\product.txt' INTO TABLE product;
LOAD DATA LOCAL INFILE 'C:\\Project_49J\\JDBC_Project\\src\\inventory.txt' INTO TABLE inventory;
LOAD DATA LOCAL INFILE 'C:\\Project_49J\\JDBC_Project\\src\\customer.txt' INTO TABLE customer;
LOAD DATA LOCAL INFILE 'C:\\Project_49J\\JDBC_Project\\src\\orders.txt' INTO TABLE orders;


