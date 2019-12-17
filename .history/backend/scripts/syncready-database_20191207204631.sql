  -- MySQL Script generated by MySQL Workbench
  -- Sun Nov 17 18:26:37 2019
  -- Model: New Model    Version: 1.0
  -- MySQL Workbench Forward Engineering

  SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
  SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
  SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

  -- -----------------------------------------------------
  -- Schema syncready_database
  -- -----------------------------------------------------
  DROP SCHEMA IF EXISTS `syncready_database` ;

  -- -----------------------------------------------------
  -- Schema syncready_database
  -- -----------------------------------------------------
  CREATE SCHEMA IF NOT EXISTS `syncready_database` DEFAULT CHARACTER SET utf8 ;
  USE `syncready_database` ;

  -- -----------------------------------------------------
  -- Table `Alerts`
  -- -----------------------------------------------------
  DROP TABLE IF EXISTS `Alerts` ;

  CREATE TABLE IF NOT EXISTS `Alerts` (
    `pk_alerts` INT NOT NULL AUTO_INCREMENT,
    `uuid_alerts` VARCHAR(255) NOT NULL,
    `alert_text` TEXT NULL,
    `created_at` DATETIME NOT NULL,
    `until_at` DATETIME NOT NULL,
    `Type_Of_Alert_uuid_type_of_alert` VARCHAR(255) NOT NULL,
    PRIMARY KEY (`pk_alerts`, `uuid_alerts`))
  ENGINE = InnoDB;


  -- -----------------------------------------------------
  -- Table `Companies`
  -- -----------------------------------------------------
  DROP TABLE IF EXISTS `Companies` ;

  CREATE TABLE IF NOT EXISTS `Companies` (
    `pk_companies` INT NOT NULL AUTO_INCREMENT,
    `uuid_company` VARCHAR(255) NOT NULL DEFAULT UUID(),
    `name` VARCHAR(100) NOT NULL,
    `company_address` VARCHAR(100) NOT NULL,
    `company_telephone` VARCHAR(100) NOT NULL,
    `company_email` VARCHAR(100) NOT NULL,
    PRIMARY KEY (`pk_companies`, `uuid_company`))
  ENGINE = InnoDB;


  -- -----------------------------------------------------
  -- Table `Datasheet`
  -- -----------------------------------------------------
  DROP TABLE IF EXISTS `Datasheet` ;

  CREATE TABLE IF NOT EXISTS `Datasheet` (
    `pk_datasheet` INT NOT NULL AUTO_INCREMENT,
    `uuid_datasheet` VARCHAR(255) NOT NULL DEFAULT UUID(),
    `description` TEXT NOT NULL,
    `created_at` DATETIME NOT NULL,
    `updated_at` DATETIME NOT NULL,
    `designation` VARCHAR(100) NOT NULL,
    PRIMARY KEY (`pk_datasheet`, `uuid_datasheet`))
  ENGINE = InnoDB;

  CREATE INDEX `uuid_datasheet_idx1` ON `Datasheet` (`uuid_datasheet` ASC);


  -- -----------------------------------------------------
  -- Table `Evaluations`
  -- -----------------------------------------------------
  DROP TABLE IF EXISTS `Evaluations` ;

  CREATE TABLE IF NOT EXISTS `Evaluations` (
    `pk_evaluation` INT NOT NULL AUTO_INCREMENT,
    `uuid_evaluation` VARCHAR(255) NOT NULL,
    `evaluation_num_stars` INT(5) NOT NULL,
    `evaluation_text` TEXT NOT NULL,
    `user_giver` VARCHAR(255) NOT NULL,
    `Rooms_uuid_room` VARCHAR(255) NOT NULL,
    PRIMARY KEY (`pk_evaluation`, `uuid_evaluation`))
  ENGINE = InnoDB;


  -- -----------------------------------------------------
  -- Table `Messages`
  -- -----------------------------------------------------
  DROP TABLE IF EXISTS `Messages` ;

  CREATE TABLE IF NOT EXISTS `Messages` (
    `pk_message` INT NOT NULL AUTO_INCREMENT,
    `content` TEXT NOT NULL,
    `created_at` DATETIME NOT NULL,
    `update_at` DATETIME NOT NULL,
    `uuid_message` VARCHAR(255) NOT NULL,
    `Status_Message_uuid_status_message` VARCHAR(100) NOT NULL,
    PRIMARY KEY (`pk_message`, `uuid_message`, `Status_Message_uuid_status_message`))
  ENGINE = InnoDB;


  -- -----------------------------------------------------
  -- Table `Messages_has_Users`
  -- -----------------------------------------------------
  DROP TABLE IF EXISTS `Messages_has_Users` ;

  CREATE TABLE IF NOT EXISTS `Messages_has_Users` (
    `Messages_uuid_message` VARCHAR(255) NOT NULL,
    `Users_pk_uuid` VARCHAR(255) NOT NULL,
    `Rooms_uuid_room` VARCHAR(255) NOT NULL,
    `sent_at` DATETIME NOT NULL,
    PRIMARY KEY (`Messages_uuid_message`, `Users_pk_uuid`))
  ENGINE = InnoDB;


  -- -----------------------------------------------------
  -- Table `Permissions`
  -- -----------------------------------------------------
  DROP TABLE IF EXISTS `Permissions` ;

  CREATE TABLE IF NOT EXISTS `Permissions` (
    `pk_permission` INT NOT NULL,
    `uuid_permission` VARCHAR(100) NOT NULL,
    `permission_designation` VARCHAR(50) NULL,
    PRIMARY KEY (`pk_permission`, `uuid_permission`))
  ENGINE = InnoDB;


  -- -----------------------------------------------------
  -- Table `Rooms`
  -- -----------------------------------------------------
  DROP TABLE IF EXISTS `Rooms` ;

  CREATE TABLE IF NOT EXISTS `Rooms` (
    `pk_rooms` INT NOT NULL AUTO_INCREMENT,
    `uuid_room` VARCHAR(255) NOT NULL DEFAULT UUID(),
    `room_code` VARCHAR(255) NOT NULL,
    `name_room` VARCHAR(100) NOT NULL,
    `created_at` DATETIME NOT NULL,
    `updated_at` DATETIME NOT NULL,
    `until_at` DATETIME NOT NULL,
    `Datasheet_uuid_datasheet` VARCHAR(255) NOT NULL,
    PRIMARY KEY (`pk_rooms`, `uuid_room`, `room_code`))
  ENGINE = InnoDB;

  CREATE INDEX `name_room_UNIQUE` ON `Rooms` (`name_room` ASC);


  -- -----------------------------------------------------
  -- Table `Status_Message`
  -- -----------------------------------------------------
  DROP TABLE IF EXISTS `Status_Message` ;

  CREATE TABLE IF NOT EXISTS `Status_Message` (
    `pk_status_message` INT NOT NULL AUTO_INCREMENT,
    `uuid_status_message` VARCHAR(100) NOT NULL,
    `designation` VARCHAR(45) NULL,
    PRIMARY KEY (`pk_status_message`, `uuid_status_message`))
  ENGINE = InnoDB;

  CREATE INDEX `uuid_status_message_idx` ON `Status_Message` (`uuid_status_message` ASC);


  -- -----------------------------------------------------
  -- Table `Status_Room`
  -- -----------------------------------------------------
  DROP TABLE IF EXISTS `Status_Room` ;

  CREATE TABLE IF NOT EXISTS `Status_Room` (
    `pk_status_room` INT NOT NULL AUTO_INCREMENT,
    `uuid_status_room` VARCHAR(45) NOT NULL,
    `status_room_designation` VARCHAR(20) NOT NULL,
    PRIMARY KEY (`pk_status_room`, `uuid_status_room`))
  ENGINE = InnoDB;

  CREATE INDEX `status_room_idx` ON `Status_Room` (`status_room_designation` ASC);


  -- -----------------------------------------------------
  -- Table `Ticket_Options`
  -- -----------------------------------------------------
  DROP TABLE IF EXISTS `Ticket_Options` ;

  CREATE TABLE IF NOT EXISTS `Ticket_Options` (
    `id_ticket_options` INT NOT NULL AUTO_INCREMENT,
    `uuid_ticket_options` VARCHAR(100) NOT NULL,
    `ticket_option_designation` VARCHAR(20) NOT NULL,
    PRIMARY KEY (`id_ticket_options`, `uuid_ticket_options`))
  ENGINE = InnoDB;


  -- -----------------------------------------------------
  -- Table `Tickets`
  -- -----------------------------------------------------
  DROP TABLE IF EXISTS `Tickets` ;

  CREATE TABLE IF NOT EXISTS `Tickets` (
    `pk_ticket` INT NOT NULL AUTO_INCREMENT,
    `uuid_ticket` VARCHAR(100) NOT NULL DEFAULT UUID(),
    `Users_pk_uuid` VARCHAR(255) NULL,
    `created_at` DATETIME NOT NULL,
    `updated_at` DATETIME NOT NULL,
    `Ticket_Options_uuid_ticket_options` VARCHAR(100) NOT NULL,
    `Rooms_uuid_room` VARCHAR(255) NOT NULL,
    PRIMARY KEY (`pk_ticket`, `uuid_ticket`))
  ENGINE = InnoDB;


  -- -----------------------------------------------------
  -- Table `Type_Of_Alert`
  -- -----------------------------------------------------
  DROP TABLE IF EXISTS `Type_Of_Alert` ;

  CREATE TABLE IF NOT EXISTS `Type_Of_Alert` (
    `pk_type_of_alert` INT NOT NULL AUTO_INCREMENT,
    `uuid_type_of_alert` VARCHAR(255) NOT NULL,
    `type_of_alert_designation` VARCHAR(50) NULL,
    PRIMARY KEY (`pk_type_of_alert`, `uuid_type_of_alert`))
  ENGINE = InnoDB;


  -- -----------------------------------------------------
  -- Table `Type_Of_User`
  -- -----------------------------------------------------
  DROP TABLE IF EXISTS `Type_Of_User` ;

  CREATE TABLE IF NOT EXISTS `Type_Of_User` (
    `pk_type_of_user` INT NOT NULL AUTO_INCREMENT,
    `uuid_type_of_users` VARCHAR(255) NOT NULL,
    `type` VARCHAR(100) NOT NULL,
    PRIMARY KEY (`pk_type_of_user`, `uuid_type_of_users`))
  ENGINE = InnoDB;


  -- -----------------------------------------------------
  -- Table `Type_Of_User_has_Alerts`
  -- -----------------------------------------------------
  DROP TABLE IF EXISTS `Type_Of_User_has_Alerts` ;

  CREATE TABLE IF NOT EXISTS `Type_Of_User_has_Alerts` (
    `Type_Of_User_uuid_type_of_users` VARCHAR(255) NOT NULL,
    `Alerts_uuid_alerts` VARCHAR(255) NOT NULL,
    PRIMARY KEY (`Type_Of_User_uuid_type_of_users`, `Alerts_uuid_alerts`))
  ENGINE = InnoDB;


  -- -----------------------------------------------------
  -- Table `Type_Of_User_has_Permissions`
  -- -----------------------------------------------------
  DROP TABLE IF EXISTS `Type_Of_User_has_Permissions` ;

  CREATE TABLE IF NOT EXISTS `Type_Of_User_has_Permissions` (
    `Type_Of_User_uuid_type_of_users` VARCHAR(255) NOT NULL,
    `Permissions_uuid_permission` VARCHAR(100) NOT NULL,
    PRIMARY KEY (`Type_Of_User_uuid_type_of_users`, `Permissions_uuid_permission`))
  ENGINE = InnoDB;


  -- -----------------------------------------------------
  -- Table `Users`
  -- -----------------------------------------------------
  DROP TABLE IF EXISTS `Users` ;

  CREATE TABLE IF NOT EXISTS `Users` (
    `pk_user` INT NOT NULL AUTO_INCREMENT,
    `pk_uuid` VARCHAR(255) NOT NULL DEFAULT UUID(),
    `nickname` VARCHAR(100) NOT NULL,
    `fullname` VARCHAR(100) NOT NULL,
    `address` VARCHAR(100) NOT NULL,
    `email` VARCHAR(100) NOT NULL,
    `telephone` VARCHAR(100) NOT NULL,
    `citizencard` VARCHAR(50) NOT NULL,
    `password` VARCHAR(50) NOT NULL,
    `created_at` DATETIME NOT NULL DEFAULT NOW(),
    `update_at` DATETIME NOT NULL DEFAULT NOW(),
    `Type_Of_User_uuid_type_of_users` VARCHAR(255) DEFAULT 1,
    PRIMARY KEY (`pk_user`, `pk_uuid`))
  ENGINE = InnoDB;

  CREATE UNIQUE INDEX `pk_uuid_UNIQUE` ON `Users` (`pk_uuid` ASC);


  -- -----------------------------------------------------
  -- Table `Users_has_Companies`
  -- -----------------------------------------------------
  DROP TABLE IF EXISTS `Users_has_Companies` ;

  CREATE TABLE IF NOT EXISTS `Users_has_Companies` (
    `Users_pk_uuid` VARCHAR(255) NOT NULL,
    `Companies_uuid_company` VARCHAR(255) NOT NULL,
    PRIMARY KEY (`Users_pk_uuid`, `Companies_uuid_company`))
  ENGINE = InnoDB;


  -- -----------------------------------------------------
  -- Table `Users_has_Rooms`
  -- -----------------------------------------------------
  DROP TABLE IF EXISTS `Users_has_Rooms` ;

  CREATE TABLE IF NOT EXISTS `Users_has_Rooms` (
    `Users_pk_uuid` VARCHAR(255) NOT NULL,
    `Rooms_uuid_room` VARCHAR(255) NOT NULL,
    PRIMARY KEY (`Users_pk_uuid`, `Rooms_uuid_room`))
  ENGINE = InnoDB;


  SET SQL_MODE=@OLD_SQL_MODE;
  SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
  SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
