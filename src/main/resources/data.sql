INSERT INTO users (username, password, email, enabled)VALUES ('user', '$2a$12$IzA1Ja1LH4PSMoro9PeITO1etDlknPjSX1nLusgt1vi9c1uaEXdEK','user@test.nl', TRUE);
INSERT INTO users (username, password, email, enabled)VALUES ('admin', '$2a$12$IzA1Ja1LH4PSMoro9PeITO1etDlknPjSX1nLusgt1vi9c1uaEXdEK', 'admin@test.nl', TRUE);

INSERT INTO authorities (username, authority) VALUES ('user', 'ROLE_USER');
INSERT INTO authorities (username, authority) VALUES ('admin', 'ROLE_USER');
INSERT INTO authorities (username, authority) VALUES ('admin', 'ROLE_ADMIN');