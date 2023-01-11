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

-- INSERT INTO grocerylists (id, name, address, products , bezorginstructies, date_time) VALUES (1,'bas','veelste dure wijk 1','vodka cola zero banaan','breng maar naar de zolderkamer',1015);
-- INSERT INTO grocerylists (id, name, address, products , bezorginstructies, date_time) VALUES (2,'jerry','achterstand wijk 2','1 bakje goesting','insert into mind', 0700);
-- INSERT INTO grocerylists (id, name, address, products , bezorginstructies, date_time) VALUES (3,'silas','Bijlmer 5','sour tangie','roll into paper', 0420);
-- INSERT INTO grocerylists (id, name, address, products , bezorginstructies, date_time) VALUES (4,'bobby','De Wallen 4','een broodje van de slager','ik loop er zelf wel om', 1600);