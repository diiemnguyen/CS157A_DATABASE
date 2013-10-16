select * from company;

select * from customer;

select * from employee;

select * from inventory;

select * from orders;

select * from product;

select * from transactions;

select * from orders, transactions;

insert into inventory (prod_Code, prod_No, prod_Title, prod_Case_Qty, prod_Case_Weight, prod_Case_Price) 
values ('2p', 204, 'Pork Butt Bone-in Nebraska', 1000 , 20, 68.00);

delete from inventory where prod_No = 0 and  prod_Title ='Pork Butt Bone-in Nebraska';


update inventory set prod_Case_Qty = 2000 , prod_Case_Price = 72.00 
where prod_No = 204 and prod_Title = 'Pork Butt Bone-in Nebraska';

insert into employee values
(1103, 11033, 'Kathleen', '1990-01-01', 'kathleen@food.com', 'Salesman Assistant', 30000);

update employee set empl_Salary = 32000  
where empl_ID = 11033 and empl_Name = 'kathleen' and dept_ID = 1103;

select * from employee as e, transactions as t where e.empl_ID = t.empl_ID;

select * from product as p, inventory as i where p.prod_Code = i.prod_Code;

delete from inventory where prod_No = 0 and  prod_Title ='Pork Butt Bone-in Nebraska';

delete from employee where empl_ID = 11033 and empl_Name = 'kathleen' and dept_ID = 1103;


insert into customer (cust_Name, cust_Email, cust_Date) 
values('Cali Market', 'cali_sale@calimarket.com', '2013-10-08');

update customer set cust_Email = 'cali_good_sale@calimarket.com' 
where cust_Name = 'Cali Market' and cust_ID = 6;

delete from customer where cust_Name = 'Cali Market' and cust_ID IN (7, 8, 9, 10, 11);


/* Run the query table 6 (customer) in companydb.sql before */
/* -------- run test trigger 1 : trigger_cust_info------------ */

#drop tables customer;

select * from customer;

/* show TriggerErrror: an empty name will cause trigger error -- correct, won't insert */ 
insert into customer(cust_Name,cust_Email) 
values('','info_sale@international.com');  

/* show TriggerErrror: an empty email will cause trigger error -- correct, won't insert */ 
insert into customer(cust_Name,cust_Email) 
values('International Supermarket','');  

/* insert successfully -- if both name and email have values */
/* do not need values for cust_ID, and cust_Date */
/* cust_ID will be auto_increment */
/* and cust_Date will be set at the day you run this query*/
insert into customer(cust_Name,cust_Email) 
values('International Supermarket','info_sale@international.com');


/* Run the query table 7 (orders) & table 3 (transactions) in companydb.sql before */
/* -------- run test trigger 2 & 3 : trigger_order & trigger_transaction ------ */

select * from inventory;

select * from transactions;

select * from customer;

#drop tables orders;

select * from orders;

/* insert successfully -- in table orders and table transactions */
/* order less than or equal 20 cases will be sold by the saleman Ken */
insert into orders(cust_Name, prod_Title, ord_Case)
values('International Supermarket','Beef Feet Sunland',30);

/* insert successfully -- in table orders and table transactions */
/* order over 20 case will be sold by the sale mananer Lucie */
insert into orders(cust_Name, prod_Title, ord_Case) 
values('International Supermarket', 'Beef Feet Sunland', 10);

/* show TriggerError: An account name is not found in customer -- correct, won't insert */
insert into orders(cust_Name, prod_Title, ord_Case)
values('','Beef Feet Sunland',30);

/* show TriggerError: The item is not found in the inventory -- correct, won't insert */
insert into orders(cust_Name, prod_Title, ord_Case)
values('International Supermarket','',30);

/* show TriggerError: order case should be positive for orders -- correct, won't insert */
insert into orders(cust_Name, prod_Title, ord_Case)
values('International Supermarket','Beef Feet Sunland',-2);

delete from customer where cust_Name = 'International supermarket' and cust_ID = 1;

