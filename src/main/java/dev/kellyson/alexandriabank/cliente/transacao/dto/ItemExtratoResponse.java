package dev.kellyson.alexandriabank.cliente.transacao.dto;

import dev.kellyson.alexandriabank.cliente.transacao.NaturezaTransacao;
import dev.kellyson.alexandriabank.cliente.transacao.TipoOperacao;
import dev.kellyson.alexandriabank.cliente.transacao.Transacao;

import java.math.BigDecimal;
import java.time.Instant;

public record ItemExtratoResponse(
        Long transacaoId,
        BigDecimal valor,
        NaturezaTransacao natureza,
        TipoOperacao tipoOperacao,
        String descricao,
        Instant realizadaEm
) {

    public static ItemExtratoResponse from(Transacao transacao) {
        return new ItemExtratoResponse(
                transacao.getId(),
                transacao.getValor(),
                transacao.getNatureza(),
                transacao.getTipoOperacao(),
                transacao.getDescricao(),
                transacao.getData()
        );
    }
}
