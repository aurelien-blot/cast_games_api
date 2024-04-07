CREATE TABLE contact (
                      id INT AUTO_INCREMENT PRIMARY KEY,
                      creation_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                      modification_time TIMESTAMP NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
                      blocked BOOLEAN NOT NULL DEFAULT FALSE,
                      active BOOLEAN NOT NULL DEFAULT FALSE,
                      player_id INT NOT NULL,
                        contact_id INT NOT NULL,
                        FOREIGN KEY (player_id) REFERENCES player(id),
                        FOREIGN KEY (contact_id) REFERENCES player(id)
);
