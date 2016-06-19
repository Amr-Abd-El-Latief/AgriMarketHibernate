DROP DATABASE IF EXISTS  `agri_project`;
CREATE DATABASE  IF NOT EXISTS `agri_project` ;
USE `agri_project`;


 
CREATE TABLE IF NOT EXISTS `credit` (
  
  `number` VARCHAR(100)  NOT NULL,
  `balance` int UNSIGNED  not null,
 PRIMARY KEY  (number)
 );

--
-- Table structure for table `user`
--



CREATE TABLE IF NOT EXISTS `user` (
  `email` VARCHAR(100) NOT NULL,
  `user_name` VARCHAR(45) NOT NULL,
  `password` VARCHAR(45) NOT NULL,
  `job` VARCHAR(45) NOT NULL,
  `address` VARCHAR(45) NOT NULL,
  `image` BLOB NOT NULL,
  `DOB` DATE NOT NULL,
 `credit_number`  VARCHAR(100)  NOT NULL,
  CONSTRAINT `credit_number_FK`
   FOREIGN KEY (`credit_number`)
   REFERENCES `agri_project`.`credit` (`number`)
   ON DELETE cascade
   ON UPDATE CASCADE,
  PRIMARY KEY (`email`));



 --
-- Table structure for table `interests`
-- 


CREATE TABLE IF NOT EXISTS `interests` (
  `email` VARCHAR(100) NOT NULL,
  `name` VARCHAR(100) NOT NULL,
CONSTRAINT   `user_interest_UQ` UNIQUE (email,name),
   CONSTRAINT `user_interest_FK`
   FOREIGN KEY (`email`)
   REFERENCES `agri_project`.`user` (`email`)
   ON DELETE cascade
   ON UPDATE CASCADE
);
  

 --
-- Table structure for table `credit`
-- 
  



 --
-- Table structure for table `category`
-- 


CREATE TABLE IF NOT EXISTS `category` (
  `id` INT(3) NOT NULL,
  `name` VARCHAR(100) NOT NULL,
   PRIMARY KEY  (id)
);



 --
-- Table structure for table `product`
-- 



CREATE TABLE IF NOT EXISTS `product` (
  `name` VARCHAR(100) NOT NULL,
  `price` FLOAT NOT NULL,
  `desc` VARCHAR(100) NOT NULL,	
  `category_id` INT(3) NOT NULL,  
   `image` BLOB NOT NULL, 
   `quantity` INT NOT NULL,
    CONSTRAINT `category_product_FK`
    FOREIGN KEY (`category_id`)
    REFERENCES `agri_project`.`category` (`id`)
    ON DELETE cascade
    ON UPDATE CASCADE,
   PRIMARY KEY  (name)
);




 --
-- Table structure for table `order`
-- 

CREATE TABLE IF NOT EXISTS `order` (
  `id` INT NOT NULL,
  `date` DATE NOT NULL,	
  `status` VARCHAR(10) NOT NULL,
   PRIMARY KEY  (id)
);



 --
-- Table structure for table `order_product`
-- 


CREATE TABLE IF NOT EXISTS `order_product` (
  `order_id` INT NOT NULL,
  `product_id` VARCHAR(100) NOT NULL,
  `user_email` VARCHAR(100) NOT NULL,  	
  `quantity` INT(10) NOT NULL,
   CONSTRAINT   `order_product_UQ` UNIQUE (order_id,product_id,user_email),   
   CONSTRAINT `order_FK`
    FOREIGN KEY (`product_id`)
    REFERENCES `agri_project`.`product` (`name`)
    ON DELETE cascade
    ON UPDATE CASCADE,
   CONSTRAINT `user_order_FK`
    FOREIGN KEY (`user_email`)
    REFERENCES `agri_project`.`user` (`email`)
    ON DELETE cascade
    ON UPDATE CASCADE,
CONSTRAINT `product_FK`
    FOREIGN KEY (`order_id`)
    REFERENCES `agri_project`.`order` (`id`)
    ON DELETE cascade
    ON UPDATE CASCADE
);

