CREATE TABLE Player (
    id SERIAL PRIMARY KEY,
    Health INT,
    Mana INT,
    Stamina INT,
    user_id INT REFERENCES users(id) ON DELETE CASCADE
);