DROP TABLE IF EXISTS item;

CREATE TABLE item(
  id SERIAL PRIMARY KEY,
  name VARCHAR(50) NOT NULL,
  description VARCHAR(255)
);