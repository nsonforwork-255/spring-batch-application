CREATE table students (

	id int8 not null primary key,
	first_name varchar null,
	last_name varchar null,
	email varchar null ,
	dept_id int8,
	is_active varchar null
)

INSERT INTO students 
(id, first_name, last_name, email, dept_id, is_active)
VALUES
(1,'John', 'Doe', 'john.doe@gmail.com', 101, 'Y'),
(2,'Jane', 'Smith', 'jane.smith@gmail.com', 102, 'Y'),
(3,'Michael', 'Johnson', 'michael.johnson@gmail.com', 101, 'N'),
(4,'Emily', 'Brown', 'emily.brown@gmail.com', 103, 'Y'),
(5,'David', 'Wilson', 'david.wilson@gmail.com', 104, 'Y'),
(6,'Sophia', 'Taylor', 'sophia.taylor@gmail.com', 102, 'N'),
(7,'Daniel', 'Anderson', 'daniel.anderson@gmail.com', 105, 'Y'),
(8,'Olivia', 'Thomas', 'olivia.thomas@gmail.com', 103, 'Y');