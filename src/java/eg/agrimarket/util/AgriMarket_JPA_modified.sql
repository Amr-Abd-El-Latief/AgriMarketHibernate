SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

DROP SCHEMA IF EXISTS `agri_project` ;
CREATE SCHEMA IF NOT EXISTS `agri_project` DEFAULT CHARACTER SET latin1 ;
USE `agri_project` ;

-- -----------------------------------------------------
-- Table `agri_project`.`category`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `agri_project`.`category` ;

CREATE  TABLE IF NOT EXISTS `agri_project`.`category` (
  `id` INT(3) NOT NULL AUTO_INCREMENT ,
  `name` VARCHAR(100) NOT NULL ,
  PRIMARY KEY (`id`) )
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `agri_project`.`credit`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `agri_project`.`credit` ;

CREATE  TABLE IF NOT EXISTS `agri_project`.`credit` (
  `number` VARCHAR(100) NOT NULL ,
  `balance` INT(10) UNSIGNED NOT NULL ,
  PRIMARY KEY (`number`) )
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `agri_project`.`interest`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `agri_project`.`interest` ;

CREATE  TABLE IF NOT EXISTS `agri_project`.`interest` (
  `id` INT NOT NULL ,
  `name` VARCHAR(45) NOT NULL ,
  PRIMARY KEY (`id`) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `agri_project`.`user`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `agri_project`.`user` ;

CREATE  TABLE IF NOT EXISTS `agri_project`.`user` (
  `email` VARCHAR(100) NOT NULL ,
  `user_name` VARCHAR(45) NOT NULL ,
  `password` VARCHAR(45) NOT NULL ,
  `job` VARCHAR(45) NOT NULL ,
  `address` VARCHAR(45) NOT NULL ,
  `image` MEDIUMBLOB NULL DEFAULT NULL ,
  `DOB` DATE NOT NULL ,
  `credit_number` VARCHAR(100) NOT NULL ,
  PRIMARY KEY (`email`) ,
  UNIQUE INDEX `user_credit_UQ` (`credit_number` ASC) ,
  CONSTRAINT `credit_number_FK`
    FOREIGN KEY (`credit_number` )
    REFERENCES `agri_project`.`credit` (`number` )
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `agri_project`.`interests`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `agri_project`.`interests` ;

CREATE  TABLE IF NOT EXISTS `agri_project`.`interests` (
  `email` VARCHAR(100) NOT NULL ,
  `interest_id` INT NOT NULL ,
  UNIQUE INDEX `user_interest_UQ` (`email` ASC, `interest_id` ASC) ,
  PRIMARY KEY (`email`, `interest_id`) ,
  INDEX `interest_id_idx` (`interest_id` ASC) ,
  INDEX `email_idx` (`email` ASC) ,
  CONSTRAINT `interest_id`
    FOREIGN KEY (`interest_id` )
    REFERENCES `agri_project`.`interest` (`id` )
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `email`
    FOREIGN KEY (`email` )
    REFERENCES `agri_project`.`user` (`email` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `agri_project`.`order`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `agri_project`.`order` ;

CREATE  TABLE IF NOT EXISTS `agri_project`.`order` (
  `id` INT(11) NOT NULL AUTO_INCREMENT ,
  `date` DATE NOT NULL ,
  `status` VARCHAR(10) NOT NULL ,
  `user_email` VARCHAR(100) NOT NULL ,
  PRIMARY KEY (`id`) ,
  INDEX `user_order_FK` (`user_email` ASC) ,
  CONSTRAINT `user_order_FK`
    FOREIGN KEY (`user_email` )
    REFERENCES `agri_project`.`user` (`email` )
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `agri_project`.`product`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `agri_project`.`product` ;

CREATE  TABLE IF NOT EXISTS `agri_project`.`product` (
  `name` VARCHAR(100) NOT NULL ,
  `price` FLOAT NOT NULL ,
  `desc` VARCHAR(100) NOT NULL ,
  `category_id` INT(3) NOT NULL ,
  `image` MEDIUMBLOB NULL DEFAULT NULL ,
  `quantity` INT(11) NOT NULL ,
  PRIMARY KEY (`name`) ,
  INDEX `category_product_FK` (`category_id` ASC) ,
  CONSTRAINT `category_product_FK`
    FOREIGN KEY (`category_id` )
    REFERENCES `agri_project`.`category` (`id` )
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;


-- -----------------------------------------------------
-- Table `agri_project`.`order_product`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `agri_project`.`order_product` ;

CREATE  TABLE IF NOT EXISTS `agri_project`.`order_product` (
  `order_id` INT(11) NOT NULL ,
  `product_id` VARCHAR(100) NOT NULL ,
  `quantity` INT(10) NOT NULL ,
  PRIMARY KEY (`order_id`, `product_id`) ,
  UNIQUE INDEX `order_product_UQ` (`order_id` ASC, `product_id` ASC) ,
  INDEX `order_FK` (`product_id` ASC) ,
  CONSTRAINT `order_FK`
    FOREIGN KEY (`product_id` )
    REFERENCES `agri_project`.`product` (`name` )
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `product_FK`
    FOREIGN KEY (`order_id` )
    REFERENCES `agri_project`.`order` (`id` )
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = latin1;

USE `agri_project` ;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
