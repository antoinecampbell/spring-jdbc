DROP TABLE IF EXISTS item;

CREATE TABLE item(
  id IDENTITY PRIMARY KEY,
  name VARCHAR(50) NOT NULL,
  description VARCHAR(255)
);