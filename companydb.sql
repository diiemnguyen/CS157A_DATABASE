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
 empl_Salary DOUBLE(10,2),
 PRIMARY KEY(dept_ID, empl_ID, empl_Name)
);


/* Table 3: transactions */
DROP TABLE IF EXISTS transactions;
CREATE TABLE transactions
(tran_ID int AUTO_INCREMENT,
 tran_Date DATE,
 ord_ID INT REFERENCES orders,
 sale_Year varchar(10),
 empl_ID INT, 
 prod_No INT, 
 cust_Name VARCHAR(100) NOT NULL REFERENCES orders,
 prod_Title VARCHAR(200),
 sale_Name VARCHAR(30), 
 sale_Case INT,
 sale_Price DOUBLE(10,2),
 sale_Amount DOUBLE(10,2),
 PRIMARY KEY(tran_ID)
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
 prod_Case_Price DOUBLE(10,2),
 PRIMARY KEY(prod_Code, prod_No, prod_Title)
); 


/* Table 6: customer */
DROP TABLE IF EXISTS customer;
CREATE TABLE customer
(cust_ID INT AUTO_INCREMENT DEFAULT NULL,
 cust_Name VARCHAR (100),
 cust_Email VARCHAR (100),
 cust_Date DATE,
 PRIMARY KEY(cust_ID)
);


/* Table 7: orders */
DROP TABLE IF EXISTS orders;
CREATE TABLE orders
(ord_ID INT AUTO_INCREMENT,
 sale_Name VARCHAR(100),
 ord_Date DATE, 
 cust_Name VARCHAR(100) NOT NULL,
 cust_Email VARCHAR(100),
 prod_No INT,
 prod_Title VARCHAR(200),
 ord_Case INT NOT NULL,
 ord_Case_Price DOUBLE(10,2) REFERENCES transactions,
 ord_Amount DOUBLE(10,2),
 PRIMARY KEY(ord_ID)
);


#START TRIGGER 1: trigger_cust_info -- check if the customer info is legal to create one.
DELIMITER //
use companydb//
DROP TRIGGER IF EXISTS trigger_cust_info//
CREATE TRIGGER trigger_cust_info BEFORE INSERT ON customer
FOR EACH ROW 
BEGIN
	declare msg varchar(100);
	set new.cust_Date = NOW();

	if ( new.cust_Name = '' ) then
		SET msg= `TriggerError: insert an empty name for customer`;
	end if;
		
	if ( new.cust_Email = '' ) then
		SET msg= `TriggerError: insert an empty email for customer`;
	end if;
END;
//
DELIMITER ;
#END TRIGGER 1


#START TRIGGER 2: trigger_order -- check if the order info is legal to make an order
delimiter //
DROP TRIGGER IF EXISTS trigger_order//
CREATE TRIGGER trigger_order BEFORE INSERT ON orders
FOR EACH ROW
BEGIN
	declare msg varchar(100);
	declare num_row int;
	declare row_item int;
	declare case_qty int;
	set new.ord_Date = now();

	select count(*) into num_row from customer as c where new.cust_Name = c.cust_Name;
	if (num_row > 0) then
		set new.cust_Email = (select max(c.cust_Email) from customer as c where new.cust_Name = c.cust_Name);
	else
		set msg= `TriggerError: An acount name is not found in customer`;
	end if;
	
	select count(*) into row_item from inventory as i where new.prod_Title = i.prod_Title;
	select max(i.prod_Case_Qty) into case_qty from inventory as i where new.prod_Title = i.prod_Title;
	if (row_item > 0) then
		if (case_qty >= new.ord_Case) then
			set new.prod_No = (select max(prod_No) from inventory where new.prod_Title = prod_Title);
			set new.ord_Case_Price = (select max(prod_Case_Price) from inventory where new.prod_Title = prod_Title);
			set new.ord_Amount = new.ord_Case_Price * new.ord_Case;
		else 
			set msg= `TriggerError: The case quantity is not enough in the inventory`;
		end if;
	else
		set msg= `TriggerError: The item is not found in inventory`;
	end if;
	
	
	if ( new.ord_Case < 0 ) then
		set msg= `TriggerError: order case should be positive for orders`;
	elseif ( new.ord_Case > 0 and new.ord_Case <= 20 ) then
		set new.sale_Name = 'Ken';
	else
		set new.sale_Name = 'Lucie';
	end if;

END;
//
delimiter ;
#END TRIGGER 2


#START TRIGGER 3: trigger_transaction -- will insert data to transactions and update inventory after the order is made
delimiter //
DROP TRIGGER IF EXISTS trigger_transaction//
CREATE TRIGGER trigger_transaction AFTER INSERT ON orders
FOR EACH ROW
BEGIN
	declare employee_ID varchar(100);
	declare year_of_sale varchar(10);
	declare in_Qty int;

	set year_of_sale = (select year(sysdate()));

	set @ord_ID = new.ord_ID;
	if ( new.ord_Case > 0 and new.ord_Case <= 20 ) then
		set @sale_Name = 'Ken';
		select max(e.empl_ID) into employee_ID from employee as e where @sale_Name = 'Ken';
		set employee_ID = 11032;
	else 
		set @sale_Name = 'Lucie';	
		select max(e.empl_ID) into employee_ID from employee as e where @sale_Name = 'Lucie';
		set employee_ID = 11031;
	end if;

	set @ord_Date = now();
	set @cust_Name = new.cust_Name;
	set @prod_No = (select max(prod_No) from inventory where prod_Title = new.prod_Title);
	set @prod_Title = (select max(prod_Title) from inventory where prod_Title = new.prod_Title);
	set @ord_Case = new.ord_Case;
	set @ord_Case_Price = (select max(i.prod_Case_Price) from inventory as i where i.prod_Title = new.prod_Title);
	set @ord_Amount = @ord_Case_Price * @ord_Case;

	insert into transactions(tran_Date, ord_ID, sale_Year, empl_ID, prod_No, cust_Name, prod_Title, sale_Name, sale_Case, sale_Price, sale_Amount) 
	values(@ord_Date, @ord_ID, year_of_sale, employee_ID, @prod_No, @cust_name, @prod_Title, @sale_Name, @ord_Case, @ord_Case_Price, @ord_Amount);

	set in_Qty = (select max(prod_Case_Qty) from inventory where prod_Title = new.prod_Title);
	update inventory set prod_Case_Qty = in_Qty - new.ord_Case where prod_Title = new.prod_Title;

END;
//
delimiter ;
#END TRIGGER 3


LOAD DATA LOCAL INFILE 'C:\\Project_49J\\JDBC_Project\\src\\company.txt' INTO TABLE company;
LOAD DATA LOCAL INFILE 'C:\\Project_49J\\JDBC_Project\\src\\employee.txt' INTO TABLE employee;
LOAD DATA LOCAL INFILE 'C:\\Project_49J\\JDBC_Project\\src\\product.txt' INTO TABLE product;
LOAD DATA LOCAL INFILE 'C:\\Project_49J\\JDBC_Project\\src\\inventory.txt' INTO TABLE inventory;
LOAD DATA LOCAL INFILE 'C:\\Project_49J\\JDBC_Project\\src\\customer.txt' INTO TABLE customer(cust_Name, cust_Email);
LOAD DATA LOCAL INFILE 'C:\\Project_49J\\JDBC_Project\\src\\orders.txt' INTO TABLE orders(cust_Name, prod_Title, ord_Case);







