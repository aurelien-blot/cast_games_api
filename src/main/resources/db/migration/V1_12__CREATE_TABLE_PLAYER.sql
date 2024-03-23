CREATE TABLE player (
                        id INT AUTO_INCREMENT PRIMARY KEY,
                        creation_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                        modification_time TIMESTAMP NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
                        user_id INT NOT NULL,
                        FOREIGN KEY(user_id) REFERENCES user(id)
);
ALTER TABLE user ADD COLUMN player_id INT NULL;
ALTER TABLE user ADD FOREIGN KEY(player_id) REFERENCES player(id);