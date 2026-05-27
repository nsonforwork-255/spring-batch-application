CREATE table students (

	id BIGINT PRIMARY KEY,
	first_name varchar(255),
	last_name varchar(255),
	email varchar(255)
)

INSERT INTO students (id, first_name, last_name, email) VALUES
(1, 'John', 'Doe', 'john.doe@gmail.com'),
(2, 'Jane', 'Smith', 'jane.smith@gmail.com'),
(3, 'Michael', 'Johnson', 'michael.johnson@gmail.com'),
(4, 'Emily', 'Brown', 'emily.brown@gmail.com'),
(5, 'David', 'Wilson', 'david.wilson@gmail.com'),
(6, 'Sophia', 'Taylor', 'sophia.taylor@gmail.com'),
(7, 'Daniel', 'Anderson', 'daniel.anderson@gmail.com'),
(8, 'Olivia', 'Thomas', 'olivia.thomas@gmail.com'),
(9, 'James', 'Jackson', 'james.jackson@gmail.com'),
(10, 'Emma', 'White', 'emma.white@gmail.com');