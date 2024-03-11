CREATE TABLE refresh_token (
    id SERIAL PRIMARY KEY,
    token VARCHAR(255) NOT NULL,
    expiry_date TIMESTAMP NOT NULL,
    user_id INT REFERENCES users(id)
);