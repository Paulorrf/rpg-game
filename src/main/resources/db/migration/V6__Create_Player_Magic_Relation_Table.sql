CREATE TABLE PlayerMagics (
    id SERIAL PRIMARY KEY,
    player_id INT REFERENCES Player(id) ON DELETE CASCADE,
    magic_id INT REFERENCES Magic(id) ON DELETE CASCADE
);