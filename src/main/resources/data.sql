INSERT INTO users (username, password, email, enabled)VALUES ('user', '$2a$12$IzA1Ja1LH4PSMoro9PeITO1etDlknPjSX1nLusgt1vi9c1uaEXdEK','user@test.nl', TRUE);
INSERT INTO users (username, password, email, enabled)VALUES ('admin', '$2a$12$IzA1Ja1LH4PSMoro9PeITO1etDlknPjSX1nLusgt1vi9c1uaEXdEK', 'admin@test.nl', TRUE);
INSERT INTO users (username, password, email, enabled)VALUES ('bas', '$2a$12$IzA1Ja1LH4PSMoro9PeITO1etDlknPjSX1nLusgt1vi9c1uaEXdEK', 'bassie@test.nl', TRUE);
INSERT INTO users (username, password, email, enabled)VALUES ('jerry', '$2a$12$IzA1Ja1LH4PSMoro9PeITO1etDlknPjSX1nLusgt1vi9c1uaEXdEK', 'jerry@test.nl', TRUE);
INSERT INTO users (username, password, email, enabled)VALUES ('bobby', '$2a$12$IzA1Ja1LH4PSMoro9PeITO1etDlknPjSX1nLusgt1vi9c1uaEXdEK', 'bobby@test.nl', TRUE);
INSERT INTO users (username, password, email, enabled)VALUES ('silas', '$2a$12$IzA1Ja1LH4PSMoro9PeITO1etDlknPjSX1nLusgt1vi9c1uaEXdEK', 'silas@test.nl', TRUE);

INSERT INTO authorities (username, authority) VALUES ('user', 'ROLE_USER');
INSERT INTO authorities (username, authority) VALUES ('admin', 'ROLE_USER');
INSERT INTO authorities (username, authority) VALUES ('admin', 'ROLE_ADMIN');
INSERT INTO authorities (username, authority) VALUES ('bas', 'ROLE_USER');
INSERT INTO authorities (username, authority) VALUES ('jerry', 'ROLE_USER');
INSERT INTO authorities (username, authority) VALUES ('bobby', 'ROLE_USER');
INSERT INTO authorities (username, authority) VALUES ('silas', 'ROLE_USER');

INSERT INTO accounts (username, name, last_name, address) VALUES ('bas','bas','van het kamp','veelste dure wijk 1');
INSERT INTO accounts (username, name, last_name, address) VALUES ('jerry','jerry','kotser','achterstand wijk 2');
INSERT INTO accounts (username, name, last_name, address) VALUES ('silas','silas','dondersgoed','Bijlmer 5');
INSERT INTO accounts (username, name, last_name, address) VALUES ('bobby','bobby','Thonnetje','De Wallen 4');
INSERT INTO accounts (username, name, last_name, address) VALUES ('admin','bassie','adriaan','fantasieland');

INSERT INTO grocery_list (name, address, delivery_instructions, date_time, products, status) VALUES ('Jan','Bij mamma zolderkamer 1','in de blauwe bak',1200, 'rijst wokgroente en kip', 'AVAILABLE');
INSERT INTO grocery_list (name, address, delivery_instructions, date_time, products, status) VALUES ('Aap','jungleboom 2','smijt maar omhoog',0600, 'bananen', 'AVAILABLE');
INSERT INTO grocery_list (name, address, delivery_instructions, date_time, products, status) VALUES ('Noot','notenboom 5','IK WIL MIJN NOTEN TERUG',1200, 'walnoten', 'AVAILABLE');
INSERT INTO grocery_list (name, address, delivery_instructions, date_time, products, status) VALUES ('Mies','Leesplankje op de basisschool','mij even oplezen ik hang bij de kleuters',1200, 'Kinderen die willen leren lezen','AVAILABLE');

INSERT INTO delivery (address) VALUES ('Bij mamma zolderkamer 1');
INSERT INTO delivery (address) VALUES ('jungleboom 2');
INSERT INTO delivery (address) VALUES ('notenboom 5');
INSERT INTO delivery (address) VALUES ('Leesplankje op de basisschool');

UPDATE users SET account_username = 'admin' WHERE username = 'admin';
UPDATE users SET account_username = 'bobby' WHERE username = 'bobby';
UPDATE users SET account_username = 'silas' WHERE username = 'silas';
UPDATE users SET account_username = 'bas' WHERE username = 'bas';
UPDATE users SET account_username = 'jerry' WHERE username = 'jerry';

