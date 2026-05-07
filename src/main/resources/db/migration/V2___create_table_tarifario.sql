CREATE TABLE tabela_tarifaria (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    data_inicio DATE NOT NULL,
    data_fim DATE,
    ativo BOOLEAN NOT NULL
);