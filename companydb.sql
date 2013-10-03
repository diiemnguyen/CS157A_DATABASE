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
(empl_ID INT,
 dept_ID INT,
 empl_Name VARCHAR (100),
 empl_DOB DATE, 
 empl_Email VARCHAR (100), 
 empl_Position VARCHAR (50), 
 empl_Salary DOUBLE,
 PRIMARY KEY(empl_ID,dept_ID,empl_DOB)
);


/* Table 3: transactions */
DROP TABLE IF EXISTS transactions;
CREATE TABLE transactions
(empl_ID INT, 
 prod_ID INT, 
 cust_ID INT, 
 sale_case INT, 
 sale_amount DOUBLE,
 PRIMARY KEY(empl_ID,prod_ID)
);


/* Table 4: product */
DROP TABLE IF EXISTS product;
CREATE TABLE product
(prod_ID INT,
 prod_NAME VARCHAR(30),
 PRIMARY KEY (prod_ID)
); 


/* Table 5: inventory */
DROP TABLE IF EXISTS inventory;
CREATE TABLE inventory
(prod_ID INT,
 prod_No INT,
 prod_Title VARCHAR (200),
 prod_Case_Qty INT,
 prod_Case_Weight INT,
 prod_Case_Price DOUBLE,
 PRIMARY KEY(prod_ID,prod_No,prod_Title)
); 


/* Table 6: customer */
DROP TABLE IF EXISTS customer;
CREATE TABLE customer
(cust_ID INT,
 cust_Name VARCHAR (100),
 cust_Email VARCHAR (100),
 PRIMARY KEY(cust_ID)
);


/* Table 7: orders */
DROP TABLE IF EXISTS orders;
CREATE TABLE orders
(cust_ID INT,
 prod_ID INT,
 cust_Total_Order INT,
 cust_Year_Order INT,
 cust_Total_Amount DOUBLE
);



LOAD DATA LOCAL INFILE 'C:\\Project_49J\\JDBC_Project\\src\\company.txt' INTO TABLE company;
LOAD DATA LOCAL INFILE 'C:\\Project_49J\\JDBC_Project\\src\\employee.txt' INTO TABLE employee;
LOAD DATA LOCAL INFILE 'C:\\Project_49J\\JDBC_Project\\src\\product.txt' INTO TABLE product;
LOAD DATA LOCAL INFILE 'C:\\Project_49J\\JDBC_Project\\src\\inventory.txt' INTO TABLE inventory;

