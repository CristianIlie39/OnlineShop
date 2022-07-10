DROP DATABASE IF EXISTS online_shop;
CREATE DATABASE IF NOT EXISTS online_shop;
USE online_shop;

CREATE TABLE IF NOT EXISTS users(
id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
name VARCHAR(30) NOT NULL,
password VARCHAR(150) NOT NULL UNIQUE,
date_of_birth DATE NOT NULL,
phone_number VARCHAR(15) NOT NULL UNIQUE,
email_address VARCHAR(30) NOT NULL UNIQUE,
address VARCHAR(100) NOT NULL,
user_role TINYINT NOT NULL,
active TINYINT NOT NULL
);

CREATE TABLE IF NOT EXISTS products(
id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
product_name VARCHAR(30) NOT NULL,
product_description VARCHAR(200) NOT NULL,
stock INT NOT NULL
);

CREATE TABLE IF NOT EXISTS purchases(
-- id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
product_name VARCHAR(30) NOT NULL,
number_of_products INT NOT NULL,
users_id INT NOT NULL,  -- I created a Many To One relationship between table purchases and table users
CONSTRAINT fk_purchases_users
FOREIGN KEY(users_id)
REFERENCES users(id),
products_id INT NOT NULL,  -- I created a Many To One relationship between table purchases and table products
CONSTRAINT fk_purchases_products
FOREIGN KEY(products_id)
REFERENCES products(id)
);

