CREATE TABLE element_type (
                      id INT AUTO_INCREMENT PRIMARY KEY,
                      creation_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                      modification_time TIMESTAMP NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
                        name VARCHAR(255) NULL,
                        shortname VARCHAR(255) NULL
);

CREATE TABLE element (
                             id INT AUTO_INCREMENT PRIMARY KEY,
                             creation_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                             modification_time TIMESTAMP NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
                             color VARCHAR(255) NULL,
                             element_type_id int NULL,
                            name VARCHAR(255) NULL,
                            quantity INT NULL,
                                FOREIGN KEY (element_type_id) REFERENCES element_type(id)
);