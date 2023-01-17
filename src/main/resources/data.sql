INSERT INTO users (username, password, email, enabled)VALUES ('user', '$2a$12$IzA1Ja1LH4PSMoro9PeITO1etDlknPjSX1nLusgt1vi9c1uaEXdEK','user@test.nl', TRUE);
INSERT INTO users (username, password, email, enabled)VALUES ('admin', '$2a$12$IzA1Ja1LH4PSMoro9PeITO1etDlknPjSX1nLusgt1vi9c1uaEXdEK', 'admin@test.nl', TRUE);

INSERT INTO authorities (username, authority) VALUES ('user', 'ROLE_USER');
INSERT INTO authorities (username, authority) VALUES ('admin', 'ROLE_USER');
INSERT INTO authorities (username, authority) VALUES ('admin', 'ROLE_ADMIN');


INSERT INTO accounts (username, name, last_name, address) VALUES ('bas','bas','van het kamp','veelste dure wijk 1');
INSERT INTO accounts (username, name, last_name, address) VALUES ('jerry','jerry','kotser','achterstand wijk 2');
INSERT INTO accounts (username, name, last_name, address) VALUES ('silas','silas','dondersgoed','Bijlmer 5');
INSERT INTO accounts (username, name, last_name, address) VALUES ('bobby','bobby','Thonnetje','De Wallen 4');
INSERT INTO accounts (username, name, last_name, address) VALUES ('admin','bassie','adriaan','fantasieland');

