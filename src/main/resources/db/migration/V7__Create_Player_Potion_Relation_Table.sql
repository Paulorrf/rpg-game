CREATE TABLE PlayerPotions (
    id SERIAL PRIMARY KEY,
    player_id INT REFERENCES Player(id) ON DELETE CASCADE,
    potion_id INT REFERENCES Potion(id) ON DELETE CASCADE,
    quantity INT
);