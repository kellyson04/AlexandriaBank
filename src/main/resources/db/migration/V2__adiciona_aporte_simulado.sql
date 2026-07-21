ALTER TABLE tb_transacoes
    DROP CONSTRAINT ck_transacoes_tipo_operacao;

ALTER TABLE tb_transacoes
    ADD CONSTRAINT ck_transacoes_tipo_operacao CHECK (
        tipo_operacao IN (
            'APORTE_SIMULADO',
            'PIX',
            'COMPRA_CARTAO',
            'PAGAMENTO_BOLETO'
        )
    );
