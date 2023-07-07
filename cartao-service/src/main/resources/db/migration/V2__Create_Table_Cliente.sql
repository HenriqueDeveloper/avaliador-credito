CREATE TABLE cliente (
    id SERIAL PRIMARY KEY,
    cpf VARCHAR(255),
    id_cartao INT,
    limite DECIMAL(19, 2),
    FOREIGN KEY (id_cartao) REFERENCES cartao(id)
);