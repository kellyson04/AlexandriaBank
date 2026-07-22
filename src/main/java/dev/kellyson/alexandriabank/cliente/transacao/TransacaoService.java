package dev.kellyson.alexandriabank.transacao;

import dev.kellyson.alexandriabank.conta.Conta;
import dev.kellyson.alexandriabank.conta.ContaRepository;
import dev.kellyson.alexandriabank.exception.BadRequestException;
import dev.kellyson.alexandriabank.exception.ResourceNotFoundException;
import dev.kellyson.alexandriabank.transacao.dto.ItemExtratoResponse;
import dev.kellyson.alexandriabank.usuario.Usuario;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransacaoService {

    private final TransacaoRepository transacaoRepository;
    private final ContaRepository contaRepository;

    public void registrarTransacao(Conta conta,
                                   BigDecimal valor,
                                   NaturezaTransacao naturezaTransacao,
                                   TipoOperacao tipoOperacao,
                                   String descricao) {
        Transacao transacao = new Transacao(conta,valor,naturezaTransacao,tipoOperacao,descricao);

        transacaoRepository.save(transacao);
    }


    public List<ItemExtratoResponse> consultarExtrato(Usuario usuario) {
        Conta conta = contaRepository.findByUsuarioId(usuario.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Conta nao encontrada"));

        return transacaoRepository.findAllByContaIdOrderByDataDesc(conta.getId())
                .stream()
                .map(ItemExtratoResponse::from)
                .toList();
    }

    @Transactional
    public void realizarPix(Usuario usuarioPagador, Long idContaDoUsuarioRecebedor, BigDecimal valor) {
        Conta contaDoUsuarioPagador = contaRepository.findByUsuarioId(usuarioPagador.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Conta do usuário pagador não encontrada"));

        if (contaDoUsuarioPagador.getId().equals(idContaDoUsuarioRecebedor)) {
            throw new BadRequestException("Nao e possivel realizar um PIX para a mesma conta.");
        }

        Conta contaDoUsuarioRecebedor = contaRepository.findById(idContaDoUsuarioRecebedor)
                .orElseThrow(() -> new ResourceNotFoundException("Conta do usuário recebedor não encontrada"));

        contaDoUsuarioPagador.debitar(valor);
        contaDoUsuarioRecebedor.creditar(valor);

        registrarTransacao(
                contaDoUsuarioPagador,valor,NaturezaTransacao.SAIDA,TipoOperacao.PIX,"PIX enviado para conta " + idContaDoUsuarioRecebedor
        );

        registrarTransacao(
            contaDoUsuarioRecebedor,valor,NaturezaTransacao.ENTRADA,TipoOperacao.PIX,"PIX recebido da conta " + contaDoUsuarioPagador.getId()
        );

    }

}
