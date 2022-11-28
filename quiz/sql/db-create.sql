DROP database IF EXISTS quiz_db;

CREATE database quiz_db;

USE quiz_db;

GRANT ALL PRIVILEGES ON quiz_db.* TO 'username'@'%';

CREATE TABLE users (
	id INT PRIMARY KEY auto_increment,
	login VARCHAR(10) UNIQUE
);

CREATE TABLE teams (
	id INT PRIMARY KEY auto_increment,
	name VARCHAR(10)
);

CREATE TABLE users_teams (
	user_id INT REFERENCES users(id) on delete cascade,
	team_id INT REFERENCES teams(id) on delete cascade,
	UNIQUE (user_id, team_id)
);

INSERT INTO users VALUES (DEFAULT, 'ivanov');
INSERT INTO users VALUES (DEFAULT, 'petrov');
INSERT INTO users VALUES (DEFAULT, 'obama');

INSERT INTO teams VALUES (DEFAULT, 'teamA');
INSERT INTO teams VALUES (DEFAULT, 'teamB');
INSERT INTO teams VALUES (DEFAULT, 'teamC');

SELECT * FROM users;
SELECT * FROM teams;
SELECT * FROM users_teams;

