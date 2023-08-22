CREATE TABLE TBL_USUARIO
(
    ID_USUARIO SERIAL PRIMARY KEY,
    TX_NOME    VARCHAR(50)  NOT NULL,
    TX_LOGIN   VARCHAR(40)  NOT NULL,
    TX_SENHA   VARCHAR(200) NOT NULL,
    TX_ROLE    VARCHAR(5)   NOT NULL
);

INSERT INTO TBL_USUARIO(TX_NOME,TX_LOGIN,TX_SENHA,TX_ROLE) VALUES ('Rafaela Buainain Luiz', 'rafa.luiz@myassist.com.br', '$2a$10$Z33ZJNM7PV6L3syOlfLptu8v0aCi4OQSuX.zvjkeSglD53wcyEUHm', 'ADMIN');
INSERT INTO TBL_USUARIO(TX_NOME,TX_LOGIN,TX_SENHA,TX_ROLE) VALUES ('Thiago Costa Torres', 'thiago.torres@myassist.com.br', '$2a$10$Z33ZJNM7PV6L3syOlfLptu8v0aCi4OQSuX.zvjkeSglD53wcyEUHm', 'USER');