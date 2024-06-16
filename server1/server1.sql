DELIMITER //
CREATE PROCEDURE generate_serial_num(OUT p_serial_num BIGINT UNSIGNED)
BEGIN
    DECLARE random_num BIGINT UNSIGNED DEFAULT 0;
    DECLARE dupl_count INT DEFAULT 0;

    REPEAT
        SET random_num = FLOOR(RAND() * 9999999999); -- 10자리 난수 생성
        SELECT COUNT(*) INTO dupl_count FROM user WHERE serial_num = random_num;
    UNTIL dupl_count = 0 END REPEAT;

    SET p_serial_num = random_num;
END//
DELIMITER ;

CREATE TABLE user (
    id INT UNSIGNED PRIMARY KEY AUTO_INCREMENT,
    serial_num BIGINT UNSIGNED UNIQUE NOT NULL,
    phone_num VARCHAR(13) UNIQUE NOT NULL,
    name VARCHAR(50) NOT NULL,
    birth_date DATE NOT NULL,
    account_info VARCHAR(255) NOT NULL,
    profile_id TINYINT NOT NULL DEFAULT 1
);

CREATE TABLE family (
    id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    user_sn BIGINT UNSIGNED NOT NULL,
    family_sn BIGINT UNSIGNED NOT NULL,
    CONSTRAINT fk_user_family FOREIGN KEY (user_sn) REFERENCES User(serial_num) ON DELETE CASCADE,
    CONSTRAINT unique_family UNIQUE (user_sn, family_sn)
);