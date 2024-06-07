CREATE TABLE `user` (
    `id` INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    `phone_num` VARCHAR(20) NOT NULL UNIQUE,
    `name` VARCHAR(255) NOT NULL,
    `birthdate` DATE NOT NULL,
    `account_info` VARCHAR(6) NOT NULL,
    `role` TINYINT NOT NULL,
    `profile_id` TINYINT NOT NULL DEFAULT 1
);