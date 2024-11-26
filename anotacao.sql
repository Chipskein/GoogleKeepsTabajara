DROP DATABASE IF EXISTS anotacoes_bnascimento;
CREATE DATABASE anotacoes_bnascimento;
\c anotacoes_bnascimento;
CREATE TABLE anotacao (
    id SERIAL PRIMARY KEY,
    titulo VARCHAR(255) NOT NULL,
    data_hora TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    descricao TEXT NOT NULL,
    cor_ansi VARCHAR(7) CHECK(
        cor_ansi = 'WHITE' OR
        cor_ansi = 'BLACK' OR
        cor_ansi = 'RED' OR
        cor_ansi = 'GREEN' OR
        cor_ansi = 'YELLOW' OR
        cor_ansi = 'BLUE' OR
        cor_ansi = 'PURPLE' OR
        cor_ansi = 'CYAN'
    ) DEFAULT 'WHITE',-- ANSI https://en.wikipedia.org/wiki/ANSI_escape_code
    foto BYTEA
);
INSERT INTO anotacao (titulo, descricao,cor_ansi) VALUES ('Anotação 1', 'Descrição da anotação 1','RED');
INSERT INTO anotacao (titulo, descricao) VALUES ('Anotação 2', 'Descrição da anotação 2');
INSERT INTO anotacao (titulo, descricao,cor_ansi) VALUES ('Anotação 3', 'Descrição da anotação 3','GREEN');

