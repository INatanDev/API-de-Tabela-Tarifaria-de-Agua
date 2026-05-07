CREATE TABLE faixa_consumo (
     id BIGSERIAL PRIMARY KEY,
     inicio INTEGER NOT NULL,
     fim INTEGER NOT NULL,
     valor_unitario NUMERIC(10,2) NOT NULL,
     categoria_id BIGINT NOT NULL,

     CONSTRAINT fk_faixa_categoria
     FOREIGN KEY (categoria_id)
     REFERENCES categoria(id)
);