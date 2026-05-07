CREATE TABLE categoria (
     id BIGSERIAL PRIMARY KEY,
     nome VARCHAR(50) NOT NULL,
     tabela_tarifaria_id BIGINT NOT NULL,

     CONSTRAINT fk_categoria_tabela
     FOREIGN KEY (tabela_tarifaria_id)
     REFERENCES tabela_tarifaria(id)
);