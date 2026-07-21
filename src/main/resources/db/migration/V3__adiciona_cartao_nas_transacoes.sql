ALTER TABLE tb_transacoes
    ADD COLUMN cartao_id BIGINT;

ALTER TABLE tb_transacoes
    ADD CONSTRAINT fk_transacoes_cartao
        FOREIGN KEY (cartao_id) REFERENCES tb_cartoes(id);

CREATE INDEX idx_transacoes_cartao_id
    ON tb_transacoes (cartao_id);
