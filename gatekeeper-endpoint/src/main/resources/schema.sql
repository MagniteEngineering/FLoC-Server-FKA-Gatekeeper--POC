CREATE TABLE session_domain (
	id serial PRIMARY KEY,
	session_id VARCHAR ( 50)  NOT NULL,
	domain VARCHAR ( 300 ) NOT NULL,
	created_on TIMESTAMP NOT NULL
);

CREATE TABLE session_cohort (
	id serial PRIMARY KEY,
	session_id VARCHAR ( 50) UNIQUE  NOT NULL,
	cohort_id VARCHAR ( 50 ) NOT NULL,
	created_on TIMESTAMP NOT NULL
);
