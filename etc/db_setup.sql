-- A script to make a fresh db
-- Please, be careful, as it drops all the current data

DROP DATABASE IF EXISTS avia;

CREATE DATABASE avia CHARACTER SET utf8 COLLATE utf8_general_ci;
USE avia;

GRANT USAGE ON *.* TO 'avia'@'localhost';
DROP USER 'avia'@'localhost';
CREATE USER 'avia'@localhost IDENTIFIED BY 'avia';
GRANT ALL ON TABLE avia.* TO 'avia'@'localhost';

-- Create tables for the login service

CREATE TABLE users(
	id INT NOT NULL AUTO_INCREMENT,
	username VARCHAR(32) NOT NULL UNIQUE,
	pwd VARCHAR(512) NOT NULL,
	CONSTRAINT users_pk PRIMARY KEY (id)
) ENGINE='InnoDB';

CREATE TABLE roles(
	id INT NOT NULL AUTO_INCREMENT,
	role VARCHAR(32) NOT NULL UNIQUE,
	CONSTRAINT roles_pk PRIMARY KEY (id)
) ENGINE='InnoDB';

CREATE TABLE roles_users(
	id INT NOT NULL AUTO_INCREMENT,
	user_id INT NOT NULL,
	role_id INT NOT NULL,
	CONSTRAINT roles_users_pk PRIMARY KEY (id),
	CONSTRAINT user_id_fk FOREIGN KEY (user_id) REFERENCES users(id),
	CONSTRAINT role_id_fk FOREIGN KEY (role_id) REFERENCES roles(id)
) ENGINE='InnoDB';

INSERT INTO users(username, pwd) VALUES ('admin','admin');
INSERT INTO users(username, pwd) VALUES ('dispatcher','dispatcher');

INSERT INTO roles(role) VALUES ('admin');
INSERT INTO roles(role) VALUES ('dispatcher');

INSERT INTO roles_users(user_id, role_id) VALUES (1, 1);
INSERT INTO roles_users(user_id, role_id) VALUES (2, 2);

-- Create tables for the flight company data

CREATE TABLE aircrafts(
	id INT NOT NULL AUTO_INCREMENT,
	model VARCHAR(15),
	reg_name VARCHAR(20) UNIQUE,
	manufacturer VARCHAR(50) NOT NULL,
	manufacture_date DATE NOT NULL,
	manufacture_country VARCHAR(50) NOT NULL,
	speed REAL,
	max_dist REAL,
	cabin_capacity TINYINT,
	pass_capacity SMALLINT,
	CONSTRAINT aircrafts_pk PRIMARY KEY (id)
) ENGINE='InnoDB';

CREATE TABLE airports(
	id INT NOT NULL AUTO_INCREMENT,
	name VARCHAR(50),
	iata_faa VARCHAR(3) NOT NULL UNIQUE,
	icao VARCHAR(4) NOT NULL UNIQUE,
	latitude DECIMAL(9,6) NOT NULL,
	longitude DECIMAL(9,6) NOT NULL,
	city VARCHAR(50),
	country VARCHAR(50),
	CONSTRAINT airports_pk PRIMARY KEY (id)
) ENGINE='InnoDB';

CREATE TABLE flights(
	id INT NOT NULL AUTO_INCREMENT,
	dep_airport_id INT NOT NULL,
	arr_airport_id INT NOT NULL,
	dep_timestamp TIMESTAMP NOT NULL,
	arr_timestamp TIMESTAMP NOT NULL CHECK (arr_timestamp > dep_timestamp),
	aircraft_id INT NOT NULL,
	CONSTRAINT flights_pk PRIMARY KEY (id),
	CONSTRAINT dep_airport_id_fk FOREIGN KEY (dep_airport_id) REFERENCES airports(id),
	CONSTRAINT arr_airport_id_fk FOREIGN KEY (arr_airport_id) REFERENCES airports(id),
	CONSTRAINT aircraft_id_fk FOREIGN KEY (aircraft_id) REFERENCES aircrafts(id)
) ENGINE='InnoDB';

CREATE INDEX dep_timestamp_idx ON flights(dep_timestamp);
CREATE INDEX arr_timestamp_idx ON flights(arr_timestamp);

CREATE TABLE positions(
	id INT NOT NULL AUTO_INCREMENT,
	name VARCHAR(20) NOT NULL UNIQUE,
	CONSTRAINT positions_pk PRIMARY KEY (id)
) ENGINE='InnoDB';

CREATE TABLE employees(
	id INT NOT NULL AUTO_INCREMENT,
	reg_code VARCHAR(10) NOT NULL UNIQUE,
	name VARCHAR(25) NOT NULL,
	surname VARCHAR(50) NOT NULL,
	gender ENUM('M','F') NOT NULL,
	position_id INT NOT NULL,
	salary DECIMAL NOT NULL,
	date_of_birth DATE NOT NULL,
	flights_performed INT NOT NULL,
	CONSTRAINT employees_pk PRIMARY KEY (id),
	CONSTRAINT position_id_fk FOREIGN KEY (position_id) REFERENCES positions(id)
) ENGINE='InnoDB';

CREATE TABLE employees_flights(
	id INT NOT NULL AUTO_INCREMENT,
	employee_id INT NOT NULL,
	flight_id INT NOT NULL,
	CONSTRAINT employees_flights_pk PRIMARY KEY (id),
	CONSTRAINT employee_id_fk FOREIGN KEY (employee_id) REFERENCES employees(id),
	CONSTRAINT flight_id_fk FOREIGN KEY (flight_id) REFERENCES flights(id)
) ENGINE='InnoDB';

-- Insert some initial data

INSERT INTO airports(name, iata_faa, icao, latitude, longitude, city, country)
VALUES ('Boryspil Intl', 'KBP', 'UKBB', 50.345, 30.894722, 'Kiev', 'Ukraine');
INSERT INTO airports(name, iata_faa, icao, latitude, longitude, city, country)
VALUES ('KIEV  INTERNATIONAL AIRPORT', 'KIP', 'KIEV', 50.1403, 30.1808, 'Kiev', 'Ukraine');
INSERT INTO airports(name, iata_faa, icao, latitude, longitude, city, country)
VALUES ('Allgau', 'FMM', 'EDJA', 47.988758, 10.2395, 'Memmingen', 'Germany');
INSERT INTO airports(name, iata_faa, icao, latitude, longitude, city, country)
VALUES ('Dortmund', 'DTM', 'EDLW', 51.518314, 7.612242, 'Dortmund', 'Germany');
INSERT INTO airports(name, iata_faa, icao, latitude, longitude, city, country)
VALUES ('Venezia Tessera', 'VCE', 'LIPZ', 45.505278, 12.351944, 'Venice', 'Italy');
INSERT INTO airports(name, iata_faa, icao, latitude, longitude, city, country)
VALUES ('Barcelona', 'BCN', 'LEBL', 41.297078, 2.078464, 'Barcelona', 'Spain');
INSERT INTO airports(name, iata_faa, icao, latitude, longitude, city, country)
VALUES ('Pyrzowice', 'KTW', 'EPKT', 50.474253, 19.080019, 'Katowice', 'Poland');
INSERT INTO airports(name, iata_faa, icao, latitude, longitude, city, country)
VALUES ('Keflavik International Airport', 'KEF', 'BIKF', 63.985, -22.605556, 'Keflavik', 'Iceland');

INSERT INTO aircrafts(model, reg_name, manufacturer, manufacture_date, manufacture_country, speed, max_dist, cabin_capacity, pass_capacity)
VALUES ('A320', 'A320-001', 'Airbus', '2000-01-01', 'European consortium', 900, 5600, 2, 179);
INSERT INTO aircrafts(model, reg_name, manufacturer, manufacture_date, manufacture_country, speed, max_dist, cabin_capacity, pass_capacity)
VALUES ('A320', 'A320-002', 'Airbus', '1998-12-31', 'European consortium', 900, 5600, 2, 179);
INSERT INTO aircrafts(model, reg_name, manufacturer, manufacture_date, manufacture_country, speed, max_dist, cabin_capacity, pass_capacity)
VALUES ('A320', 'A320-003', 'Airbus', '2000-01-30', 'European consortium', 900, 5600, 2, 179);
INSERT INTO aircrafts(model, reg_name, manufacturer, manufacture_date, manufacture_country, speed, max_dist, cabin_capacity, pass_capacity)
VALUES ('A320', 'A320-004', 'Airbus', '1998-09-01', 'European consortium', 900, 5600, 2, 179);
INSERT INTO aircrafts(model, reg_name, manufacturer, manufacture_date, manufacture_country, speed, max_dist, cabin_capacity, pass_capacity)
VALUES ('737-900', '737-001', 'Boeing', '2010-07-10', 'USA', 833, 3800, 2, 177);
INSERT INTO aircrafts(model, reg_name, manufacturer, manufacture_date, manufacture_country, speed, max_dist, cabin_capacity, pass_capacity)
VALUES ('737-800', '737-002', 'Boeing', '2005-11-11', 'USA', 833, 3800, 2, 177);
INSERT INTO aircrafts(model, reg_name, manufacturer, manufacture_date, manufacture_country, speed, max_dist, cabin_capacity, pass_capacity)
VALUES ('Bravo', 'Bravo-001', 'Cessna', '2004-03-08', 'USA', 750, 3500, 2, 6);

INSERT INTO positions(name) VALUES ('Pilot');
INSERT INTO positions(name) VALUES ('Flight Attendant');

INSERT INTO employees(reg_code, name, surname, gender, position_id, salary, date_of_birth, flights_performed)
VALUES ('AA00000001', 'Eric', 'Cartman', 'M', 1, 5000, '1970-05-12', 1234);
INSERT INTO employees(reg_code, name, surname, gender, position_id, salary, date_of_birth, flights_performed)
VALUES ('AA00000002', 'Stan', 'Marsh', 'M', 1, 6000, '1972-08-01', 1567);
INSERT INTO employees(reg_code, name, surname, gender, position_id, salary, date_of_birth, flights_performed)
VALUES ('AA00000003', 'Kyle', 'Broflovski', 'M', 1, 4500, '1985-05-31', 809);
INSERT INTO employees(reg_code, name, surname, gender, position_id, salary, date_of_birth, flights_performed)
VALUES ('AA00000004', 'Kenny', 'McCormick', 'M', 1, 5000, '1976-09-09', 1198);
INSERT INTO employees(reg_code, name, surname, gender, position_id, salary, date_of_birth, flights_performed)
VALUES ('AA00000005', 'Philip', 'Fry', 'M', 1, 5000, '1967-06-14', 1302);
INSERT INTO employees(reg_code, name, surname, gender, position_id, salary, date_of_birth, flights_performed)
VALUES ('AA00000006', 'Turanga', 'Leela', 'F', 1, 5500, '1965-02-25', 1407);
INSERT INTO employees(reg_code, name, surname, gender, position_id, salary, date_of_birth, flights_performed)
VALUES ('AA00000007', 'Zapp', 'Brannigan', 'M', 1, 5500, '1971-07-29', 1396);
INSERT INTO employees(reg_code, name, surname, gender, position_id, salary, date_of_birth, flights_performed)
VALUES ('AA00000008', 'Джон', 'Зойдберг', 'M', 2, 3500, '1988-01-17', 579);
INSERT INTO employees(reg_code, name, surname, gender, position_id, salary, date_of_birth, flights_performed)
VALUES ('AA00000009', 'Бендер', 'Родригес', 'M', 2, 3000, '1990-10-31', 455);
INSERT INTO employees(reg_code, name, surname, gender, position_id, salary, date_of_birth, flights_performed)
VALUES ('AA00000010', 'Киф', 'Крокер', 'M', 2, 2000, '1991-11-19', 297);

INSERT INTO employees(reg_code, name, surname, gender, position_id, salary, date_of_birth, flights_performed)
VALUES ('AA00000011', 'Эми', 'Вонг', 'F', 2, 4000, '1970-09-13', 1174);
INSERT INTO employees(reg_code, name, surname, gender, position_id, salary, date_of_birth, flights_performed)
VALUES ('AA00000012', 'Натали', 'Дормер', 'F', 2, 5000, '1973-12-05', 1729);
INSERT INTO employees(reg_code, name, surname, gender, position_id, salary, date_of_birth, flights_performed)
VALUES ('AA00000013', 'Emilia', 'Clark', 'F', 2, 3500, '1985-03-31', 871);
INSERT INTO employees(reg_code, name, surname, gender, position_id, salary, date_of_birth, flights_performed)
VALUES ('AA00000014', 'Emma', 'Watson', 'F', 2, 4000, '1976-09-09', 1301);
INSERT INTO employees(reg_code, name, surname, gender, position_id, salary, date_of_birth, flights_performed)
VALUES ('AA00000015', 'Jeniffer', 'Lawrence', 'F', 2, 4000, '1977-06-08', 1290);
INSERT INTO employees(reg_code, name, surname, gender, position_id, salary, date_of_birth, flights_performed)
VALUES ('AA00000016', 'Monica', 'Belucci', 'F', 2, 4500, '1978-02-16', 1431);
INSERT INTO employees(reg_code, name, surname, gender, position_id, salary, date_of_birth, flights_performed)
VALUES ('AA00000017', 'Софи', 'Марсо', 'F', 2, 4500, '1980-07-12', 1321);
INSERT INTO employees(reg_code, name, surname, gender, position_id, salary, date_of_birth, flights_performed)
VALUES ('AA00000018', 'Эдвиж', 'Фенек', 'F', 2, 2500, '1989-01-23', 631);
INSERT INTO employees(reg_code, name, surname, gender, position_id, salary, date_of_birth, flights_performed)
VALUES ('AA00000019', 'Орнелла', 'Мутти', 'F', 2, 2000, '1990-10-10', 278);
INSERT INTO employees(reg_code, name, surname, gender, position_id, salary, date_of_birth, flights_performed)
VALUES ('AA00000020', 'Viktoria', 'Kerekes', 'F', 2, 1000, '1993-07-19', 52);

INSERT INTO flights(dep_airport_id, arr_airport_id, dep_timestamp, arr_timestamp, aircraft_id)
VALUES (1, 4, NOW() + INTERVAL 7 HOUR, NOW() + INTERVAL 12 HOUR, 1);
INSERT INTO flights(dep_airport_id, arr_airport_id, dep_timestamp, arr_timestamp, aircraft_id)
VALUES (1, 5, NOW() + INTERVAL 15 HOUR, NOW() + INTERVAL 18 HOUR, 2);
INSERT INTO flights(dep_airport_id, arr_airport_id, dep_timestamp, arr_timestamp, aircraft_id)
VALUES (1, 8, NOW() + INTERVAL 6 HOUR, NOW() + INTERVAL 10 HOUR, 3);
INSERT INTO flights(dep_airport_id, arr_airport_id, dep_timestamp, arr_timestamp, aircraft_id)
VALUES (2, 3, NOW() - INTERVAL 8 HOUR, NOW() - INTERVAL 3 HOUR, 4);
INSERT INTO flights(dep_airport_id, arr_airport_id, dep_timestamp, arr_timestamp, aircraft_id)
VALUES (2, 6, NOW() - INTERVAL 8 HOUR, NOW() - INTERVAL 3 HOUR, 4);
INSERT INTO flights(dep_airport_id, arr_airport_id, dep_timestamp, arr_timestamp, aircraft_id)
VALUES (2, 7, NOW() + INTERVAL 13 HOUR, NOW() + INTERVAL 17 HOUR, 5);

INSERT INTO employees_flights(employee_id, flight_id) VALUES(1, 1);
INSERT INTO employees_flights(employee_id, flight_id) VALUES(3, 1);
INSERT INTO employees_flights(employee_id, flight_id) VALUES(10, 1);
INSERT INTO employees_flights(employee_id, flight_id) VALUES(2, 2);
INSERT INTO employees_flights(employee_id, flight_id) VALUES(3, 3);
INSERT INTO employees_flights(employee_id, flight_id) VALUES(17, 3);
INSERT INTO employees_flights(employee_id, flight_id) VALUES(15, 2);