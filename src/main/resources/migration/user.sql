CREATE TABLE users (
                       user_id BIGINT NOT NULL AUTO_INCREMENT,
                       password VARCHAR(64) NOT NULL,
                       user_name VARCHAR(20),
                       role VARCHAR(5),
                       available_vac_days DECIMAL(10, 2) DEFAULT 15.0,
                       requested_vac_days DECIMAL(10, 2) DEFAULT 0.0,
                       created_at DATETIME,
                       updated_at DATETIME,
                       PRIMARY KEY (user_id)
) ENGINE=InnoDB;
