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


/* Table 2: employees */
DROP TABLE IF EXISTS employees;
CREATE TABLE employees
(empl_ID INT,
 dept_ID INT,
 empl_Name VARCHAR (100),
 empl_DOB DATE, 
 empl_Email VARCHAR (100), 
 empl_Position VARCHAR (50), 
 empl_Salary DOUBLE,
 PRIMARY KEY(empl_ID,dept_ID)
);


/* Table 3: sale_transactions */
DROP TABLE IF EXISTS sale_transactions;
CREATE TABLE sale_transactions
(empl_ID INT, 
 prod_ID INT, 
 cust_ID INT, 
 sale_case INT, 
 sale_amount DOUBLE,
 PRIMARY KEY(empl_ID,prod_ID)
);


/* Table 4: products */
DROP TABLE IF EXISTS products;
CREATE TABLE products
(prod_ID INT,
 prod_NAME VARCHAR(30),
 PRIMARY KEY (prod_ID)
); 


/* Table 5: product_lists */
DROP TABLE IF EXISTS product_lists;
CREATE TABLE product_lists
(prod_ID INT,
 prod_No INT,
 prod_Title VARCHAR (200),
 prod_Case_Qty INT,
 prod_Case_Weight INT,
 prod_Case_Price DOUBLE,
 PRIMARY KEY(prod_ID,prod_No,prod_Title)
); 


/* Table 6: customers */
DROP TABLE IF EXISTS customers;
CREATE TABLE customers
(cust_ID INT,
 cust_Name VARCHAR (100),
 cust_Email VARCHAR (100),
 PRIMARY KEY(cust_ID)
);


/* Table 7: customer_orders */
DROP TABLE IF EXISTS customer_orders;
CREATE TABLE customer_orders
(cust_ID INT,
 prod_ID INT,
 cust_Total_Order INT,
 cust_Year_Order INT,
 cust_Total_Amount DOUBLE
);



LOAD DATA LOCAL INFILE 'C:\\Project_49J\\JDBC_Project\\src\\company.txt' INTO TABLE company;
LOAD DATA LOCAL INFILE 'C:\\Project_49J\\JDBC_Project\\src\\products.txt' INTO TABLE products;
LOAD DATA LOCAL INFILE 'C:\\Project_49J\\JDBC_Project\\src\\product_lists.txt' INTO TABLE product_lists;
