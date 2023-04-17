DROP DATABASE IF EXISTS java_33_34_pet_manager;
CREATE DATABASE IF NOT EXISTS java_33_34_pet_manager;

USE java_33_34_pet_manager;

/*
tables to create
 owners, types of pet, pets,  meals, toys
 */

CREATE TABLE IF NOT EXISTS owners (
                                      id INT AUTO_INCREMENT NOT NULL,
                                      ownerName VARCHAR(100) NOT NULL,
    age INTEGER NOT NULL,
    email VARCHAR(255) UNIQUE,
    createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updatedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY(id)
    );

CREATE TABLE IF NOT EXISTS petTypes (
                                        id INT AUTO_INCREMENT NOT NULL,
                                        type VARCHAR(100) NOT NULL,
    PRIMARY KEY(id)
    );

CREATE TABLE IF NOT EXISTS pets (
                                    id INT AUTO_INCREMENT NOT NULL,
                                    petName VARCHAR(100) NOT NULL,
    birthDate DATE NOT NULL,
    weight DOUBLE,
    petTypeId INT NOT NULL,
    ownerId INT,
    PRIMARY KEY(id),
    FOREIGN KEY(petTypeId) REFERENCES petTypes(id),
    FOREIGN KEY(ownerId) REFERENCES owners(id)
    );

CREATE TABLE IF NOT EXISTS meals (
                                     id INT AUTO_INCREMENT NOT NULL,
                                     mealName VARCHAR(100) NOT NULL,
    petTypeId INT NOT NULL,
    description TEXT,
    calories DOUBLE,
    PRIMARY KEY(id),
    FOREIGN KEY(petTypeId) REFERENCES petTypes(id)
    );


CREATE TABLE IF NOT EXISTS toys (
                                    id INT AUTO_INCREMENT NOT NULL,
                                    toyName VARCHAR(100) NOT NULL,
    description TEXT,
    price DOUBLE NOT NULL,
    petTypeId INT NOT NULL,
    PRIMARY KEY(id),
    FOREIGN KEY(petTypeId) REFERENCES petTypes(id)
    );

ALTER TABLE petTypes ADD createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP;
ALTER TABLE meals ADD createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP;
ALTER TABLE toys ADD createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP;
ALTER TABLE pets ADD createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP;

ALTER TABLE petTypes ADD updatedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP;
ALTER TABLE meals ADD updatedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP;
ALTER TABLE toys ADD updatedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP;
ALTER TABLE pets ADD updatedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP;

ALTER TABLE meals DROP COLUMN description;

ALTER TABLE meals ADD description VARCHAR(255);

SELECT * FROM owners;
SELECT * FROM petTypes;
SELECT * FROM meals;
SELECT * FROM toys;
SELECT * FROM pets;
