CREATE TABLE despesa (
  id bigserial PRIMARY KEY,
  data DATE NOT NULL,
  valor BIGINT NOT NULL,
  descricao VARCHAR(200) NOT NULL,
  categoria VARCHAR(50) NOT NULL,
  paga BOOLEAN NOT NULL DEFAULT FALSE,
  CONSTRAINT despesa_uk UNIQUE (data, descricao, categoria)
);

CREATE INDEX categoria_idx on despesa (categoria);
CREATE INDEX data_idx on despesa (data);