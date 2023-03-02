CREATE TABLE vacations (
                           vacation_id BIGINT NOT NULL AUTO_INCREMENT,
                           vacation_type VARCHAR(20),
                           status VARCHAR(10),
                           days_used DECIMAL(10, 2) NOT NULL,
                           start_date DATE NOT NULL,
                           end_date DATE NOT NULL,
                           user_id BIGINT,
                           comment VARCHAR(500),
                           created_at DATETIME,
                           updated_at DATETIME,
                           PRIMARY KEY (vacation_id),
                           CONSTRAINT vacations_ibfk_1 FOREIGN KEY (user_id) REFERENCES users (user_id)
) ENGINE=InnoDB;
