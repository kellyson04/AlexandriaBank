package dev.kellyson.alexandriabank.cliente.cartao;

import dev.kellyson.alexandriabank.cliente.conta.Conta;
import dev.kellyson.alexandriabank.cliente.conta.ContaRepository;
import dev.kellyson.alexandriabank.cliente.conta.StatusConta;
import dev.kellyson.alexandriabank.cliente.transacao.TransacaoService;
import dev.kellyson.alexandriabank.cliente.transacao.dto.ItemExtratoResponse;
import dev.kellyson.alexandriabank.exception.BusinessRuleException;
import dev.kellyson.alexandriabank.exception.ConflictException;
import dev.kellyson.alexandriabank.exception.ResourceNotFoundException;
import dev.kellyson.alexandriabank.usuario.Usuario;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CartaoService {

    private static final int QUANTIDADE_DIGITOS_NUMERO_CARTAO = 16;
    private static final int ANOS_DE_VALIDADE = 5;
    private static final SecureRandom RANDOM = new SecureRandom();

    private final CartaoRepository cartaoRepository;
    private final ContaRepository contaRepository;
    private final TransacaoService transacaoService;

    public void solicitarCartao(Usuario usuario) {
        Conta conta = contaRepository.findByUsuarioId(usuario.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Conta nao encontrada"));

        if (conta.getStatus() != StatusConta.ATIVA) {
            throw new BusinessRuleException("A conta precisa estar ativa para solicitar um cartao");
        }

        if (cartaoRepository.existsByContaId(conta.getId())) {
            throw new ConflictException("A conta ja possui um cartao");
        }

        Cartao cartao = new Cartao(
                gerarNumeroUnico(),
                conta.getUsuario().getNome(),
                LocalDate.now().plusYears(ANOS_DE_VALIDADE),
                conta
        );

        cartaoRepository.save(cartao);
    }

    public void bloquearCartao(Usuario usuario) {
        Cartao cartao = cartaoRepository.findByContaUsuarioId(usuario.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Cartao nao encontrado"));

        cartao.bloquear();

        cartaoRepository.save(cartao);
    }

    public void desbloquearCartao(Usuario usuario) {
        Cartao cartao = cartaoRepository.findByContaUsuarioId(usuario.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Cartao nao encontrado"));

        cartao.desbloquear();

        cartaoRepository.save(cartao);
    }

    public List<ItemExtratoResponse> consultarExtrato(Usuario usuario) {
        Cartao cartao = cartaoRepository.findByContaUsuarioId(usuario.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Cartao nao encontrado"));

        return transacaoService.consultarExtratoCartao(cartao.getId());
    }

    private String gerarNumeroUnico() {
        String numero;

        do {
            StringBuilder numeroGerado = new StringBuilder(QUANTIDADE_DIGITOS_NUMERO_CARTAO);

            for (int i = 0; i < QUANTIDADE_DIGITOS_NUMERO_CARTAO; i++) {
                numeroGerado.append(RANDOM.nextInt(10));
            }

            numero = numeroGerado.toString();
        } while (cartaoRepository.existsByNumero(numero));

        return numero;
    }
}
