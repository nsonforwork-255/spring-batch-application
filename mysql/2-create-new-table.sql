CREATE table students (

	id BIGINT PRIMARY KEY,
	first_name varchar(255),
	last_name varchar(255),
	email varchar(255),
	dept_id BIGINT,
	is_active boolean
)