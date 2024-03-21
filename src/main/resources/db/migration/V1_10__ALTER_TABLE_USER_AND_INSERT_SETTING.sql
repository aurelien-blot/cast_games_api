ALTER TABLE user ADD COLUMN reset_password_token VARCHAR(255) NULL;
INSERT INTO setting (short_name, label, value, description) VALUES
    ('mailjet_reset_password_id', 'Mailjet Reset Password Template ID', '5809825', 'The ID of the Mailjet template to use for reset password emails.');