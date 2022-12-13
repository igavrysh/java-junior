DROP database IF EXISTS exampledb;

CREATE database exampledb;

USE exampledb;

GRANT ALL PRIVILEGES ON exampledb.* TO 'user'@'%';

CREATE TABLE employee (
	emp_id INT PRIMARY KEY auto_increment,
	emp_name VARCHAR(255),
	salary FLOAT,
	dept_name VARCHAR(255)
);