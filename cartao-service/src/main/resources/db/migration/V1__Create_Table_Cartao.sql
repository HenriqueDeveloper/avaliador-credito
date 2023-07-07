CREATE TABLE cartao (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(255),
    bandeira VARCHAR(255),
    renda DECIMAL(19, 2),
    limite DECIMAL(19, 2)
);