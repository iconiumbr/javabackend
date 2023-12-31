-- V1.2__criar_servico_tecnico.sql
CREATE TABLE TBL_SERVICO
(
    ID_SERVICO   NUMBER GENERATED ALWAYS AS IDENTITY,
    TX_DESCRICAO VARCHAR2(80) NOT NULL,
    NR_VALOR     NUMBER(19,4) NOT NULL,
    CONSTRAINT ID_SERVICO PRIMARY KEY (ID_SERVICO)
);

INSERT INTO TBL_SERVICO(TX_DESCRICAO,NR_VALOR) VALUES ('Formatação de Sistemas Operacionais',50.00);
INSERT INTO TBL_SERVICO(TX_DESCRICAO,NR_VALOR) VALUES ('Instalação do Microsoft Windows',100.00);
INSERT INTO TBL_SERVICO(TX_DESCRICAO,NR_VALOR) VALUES ('Backup de arquivos e e-mails',50.00);
INSERT INTO TBL_SERVICO(TX_DESCRICAO,NR_VALOR) VALUES ('Instalação de programas diversos',30.00);
INSERT INTO TBL_SERVICO(TX_DESCRICAO,NR_VALOR) VALUES ('Remoção de vírus e ameaças',80.00);
INSERT INTO TBL_SERVICO(TX_DESCRICAO,NR_VALOR) VALUES ('Upgrade de peças e componentes',120.00);
INSERT INTO TBL_SERVICO(TX_DESCRICAO,NR_VALOR) VALUES ('Reparos em Placa-mãe',200.00);
INSERT INTO TBL_SERVICO(TX_DESCRICAO,NR_VALOR) VALUES ('Limpeza e lubrificação interna',100.00);
INSERT INTO TBL_SERVICO(TX_DESCRICAO,NR_VALOR) VALUES ('Configuração de periféricos',40.00);
INSERT INTO TBL_SERVICO(TX_DESCRICAO,NR_VALOR) VALUES ('Correção de lentidão e travamentos',80.00);
INSERT INTO TBL_SERVICO(TX_DESCRICAO,NR_VALOR) VALUES ('Indicação para upgrade e substituição de peças',30.00);
INSERT INTO TBL_SERVICO(TX_DESCRICAO,NR_VALOR) VALUES ('Configuração de roteador wireless',15.00);

CREATE TABLE TBL_TECNICO
(
    ID_TECNICO   NUMBER GENERATED ALWAYS AS IDENTITY,
    TX_NOME      VARCHAR2(45) NOT NULL,
    TX_AREA_FONE VARCHAR2(2),
    TX_FONE      VARCHAR2(9),
    TX_TIPO_FONE VARCHAR2(11),
    TX_EMAIL     VARCHAR2(50),
    CONSTRAINT ID_TECNICO PRIMARY KEY (ID_TECNICO)
);

INSERT INTO TBL_TECNICO(TX_NOME,TX_EMAIL) VALUES ('Rafaela Buainain Luiz','rafa.luiz@myassist.com.br');
INSERT INTO TBL_TECNICO(TX_NOME,TX_EMAIL) VALUES ('Diogo Castro Lopes','diogo.lopes@myassist.com.br');
INSERT INTO TBL_TECNICO(TX_NOME,TX_EMAIL) VALUES ('Felipe Kenji Yoshida','felipe.yoshida@myassist.com.br');
INSERT INTO TBL_TECNICO(TX_NOME,TX_EMAIL) VALUES ('Maria Luiza De Sa Barros','maria.barros@myassist.com.br');
INSERT INTO TBL_TECNICO(TX_NOME,TX_EMAIL) VALUES ('Thiago Costa Torres','thiago.torres@myassist.com.br');

---------------------------------------------------------------------------------------