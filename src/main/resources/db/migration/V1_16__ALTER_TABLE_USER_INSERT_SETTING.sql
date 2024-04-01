ALTER TABLE user ADD COLUMN blocked BOOLEAN DEFAULT FALSE NOT NULL,
 ADD COLUMN tentatives INT DEFAULT 0 NOT NULL;
INSERT INTO setting (short_name, label, value, description) VALUES ('tentatives_before_blocking', 'Tentatives avant blocage', null, 'Tentatives avant blocage du compte');