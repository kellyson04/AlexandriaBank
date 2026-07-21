CREATE TABLE tb_usuarios (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(120) NOT NULL,
    cpf VARCHAR(11) NOT NULL UNIQUE,
    email VARCHAR(180) NOT NULL UNIQUE,
    senha VARCHAR(255) NOT NULL,
    perfil VARCHAR(20) NOT NULL,
    criado_em TIMESTAMP WITH TIME ZONE NOT NULL,

    CONSTRAINT ck_usuarios_perfil CHECK (perfil IN ('ADMIN', 'CLIENTE'))
);

CREATE TABLE tb_contas (
    id BIGSERIAL PRIMARY KEY,
    usuario_id BIGINT NOT NULL UNIQUE,
    saldo NUMERIC(19, 2) NOT NULL,
    status VARCHAR(255) NOT NULL,
    criada_em TIMESTAMP WITH TIME ZONE NOT NULL,

    FOREIGN KEY (usuario_id) REFERENCES tb_usuarios(id),
    CONSTRAINT ck_contas_saldo_nao_negativo CHECK (saldo >= 0),
    CONSTRAINT ck_contas_status CHECK (status IN ('ATIVA', 'BLOQUEADA', 'ENCERRADA'))
);

CREATE TABLE tb_transacoes (
    id BIGSERIAL PRIMARY KEY,
    conta_id BIGINT NOT NULL,
    valor NUMERIC(19, 2) NOT NULL,
    natureza VARCHAR(20) NOT NULL,
    tipo_operacao VARCHAR(30) NOT NULL,
    data TIMESTAMP WITH TIME ZONE NOT NULL,
    descricao VARCHAR(255) NOT NULL,

    FOREIGN KEY (conta_id) REFERENCES tb_contas(id),
    CONSTRAINT ck_transacoes_valor_positivo CHECK (valor > 0),
    CONSTRAINT ck_transacoes_natureza CHECK (natureza IN ('ENTRADA', 'SAIDA')),
    CONSTRAINT ck_transacoes_tipo_operacao CHECK (
        tipo_operacao IN ('PIX', 'COMPRA_CARTAO', 'PAGAMENTO_BOLETO')
    )
);

CREATE TABLE tb_cartoes (
    id BIGSERIAL PRIMARY KEY,
    conta_id BIGINT NOT NULL UNIQUE,
    numero VARCHAR(16) NOT NULL UNIQUE,
    nome_impresso VARCHAR(120) NOT NULL,
    data_validade DATE NOT NULL,
    status VARCHAR(20) NOT NULL,
    data_solicitacao TIMESTAMP WITH TIME ZONE NOT NULL,

    FOREIGN KEY (conta_id) REFERENCES tb_contas(id),
    CONSTRAINT ck_cartoes_status CHECK (status IN ('ATIVO', 'BLOQUEADO'))
);
