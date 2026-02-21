CREATE TABLE locations
(
    id        INT PRIMARY KEY AUTO_INCREMENT,
    name      VARCHAR(255)  NOT NULL,
    user_id   INT           NOT NULL,
    latitude  DECIMAL(6, 4) NOT NULL,
    longitude DECIMAL(7, 4) NOT NULL,

    FOREIGN KEY (user_id) REFERENCES users (id)
);