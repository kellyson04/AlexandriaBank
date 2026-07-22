package dev.kellyson.alexandriabank.transacao.dto;

import dev.kellyson.alexandriabank.transacao.NaturezaTransacao;
import dev.kellyson.alexandriabank.transacao.TipoOperacao;
import dev.kellyson.alexandriabank.transacao.Transacao;

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
