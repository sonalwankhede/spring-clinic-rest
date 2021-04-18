DROP TABLE visits IF EXISTS;
DROP TABLE roles IF EXISTS;
DROP TABLE users IF EXISTS;
DROP TABLE patients IF EXISTS;
DROP TABLE issues IF EXISTS;
DROP TABLE allergies IF EXISTS;
DROP TABLE drugs IF EXISTS;
DROP TABLE prescriptions IF EXISTS;
DROP TABLE diagnosis IF EXISTS;


CREATE TABLE issues (
	id INTEGER IDENTITY PRIMARY KEY,
	issues VARCHAR(255)
);

CREATE TABLE allergies (
	id INTEGER IDENTITY PRIMARY KEY,
	allergy VARCHAR(255)
);

CREATE TABLE patients (
  id         INTEGER IDENTITY PRIMARY KEY,
  first_name VARCHAR(30),
  last_name  VARCHAR(30),
  birth_date VARCHAR(30),
  gender	VARCHAR(30),
  address    VARCHAR(255),
  city       VARCHAR(80),
  telephone  VARCHAR(20),
  history VARCHAR(255),
  allergies VARCHAR(255)
);
CREATE INDEX patients_last_name ON patients (last_name);

CREATE TABLE drugs (
  id		INTEGER IDENTITY PRIMARY KEY,
  name		VARCHAR(255),
  brand_name		VARCHAR(255),
  form_of_drugs				VARCHAR(30),
  strength	VARCHAR(30),
  content   VARCHAR(255)
 );
CREATE INDEX drugs_brand_name ON drugs (brand_name);

CREATE TABLE prescriptions (
  id		INTEGER IDENTITY PRIMARY KEY,
  drug_id  	INTEGER,
  timing 	VARCHAR(255)
);

CREATE TABLE visits (
  id          INTEGER IDENTITY PRIMARY KEY,
  patient_id      INTEGER NOT NULL,
  visit_date  DATE,
  description VARCHAR(255),
  diagnosis VARCHAR(255),
  prescription_id INTEGER
);
--ALTER TABLE visits ADD CONSTRAINT fk_visits_patients FOREIGN KEY (patient_id) REFERENCES patients (id);
CREATE INDEX visits_patient_id ON visits (patient_id);

CREATE  TABLE users (
  username    VARCHAR(20) NOT NULL ,
  password    VARCHAR(20) NOT NULL ,
  enabled     BOOLEAN DEFAULT TRUE NOT NULL ,
  PRIMARY KEY (username)
);

CREATE TABLE roles (
  id              INTEGER IDENTITY PRIMARY KEY,
  username        VARCHAR(20) NOT NULL,
  role            VARCHAR(20) NOT NULL
);
ALTER TABLE roles ADD CONSTRAINT fk_username FOREIGN KEY (username) REFERENCES users (username);
CREATE INDEX fk_username_idx ON roles (username);


CREATE TABLE diagnosis (
	id	INTEGER IDENTITY PRIMARY KEY,
	diagnosis     VARCHAR(30)
);