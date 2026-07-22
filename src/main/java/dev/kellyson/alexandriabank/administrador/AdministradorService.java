package dev.kellyson.alexandriabank.administrador;

import dev.kellyson.alexandriabank.administrador.dto.CartaoAdministrativoResponse;
import dev.kellyson.alexandriabank.administrador.dto.ClienteAdministrativoResponse;
import dev.kellyson.alexandriabank.administrador.dto.ContaAdministrativaResponse;
import dev.kellyson.alexandriabank.cliente.cartao.Cartao;
import dev.kellyson.alexandriabank.cliente.cartao.CartaoRepository;
import dev.kellyson.alexandriabank.cliente.conta.Conta;
import dev.kellyson.alexandriabank.cliente.conta.ContaRepository;
import dev.kellyson.alexandriabank.exception.ResourceNotFoundException;
import dev.kellyson.alexandriabank.cliente.transacao.TransacaoRepository;
import dev.kellyson.alexandriabank.cliente.transacao.dto.ItemExtratoResponse;
import dev.kellyson.alexandriabank.usuario.PerfilUsuario;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdministradorService {

    private final ContaRepository contaRepository;
    private final TransacaoRepository transacaoRepository;
    private final CartaoRepository cartaoRepository;

    public List<ClienteAdministrativoResponse> consultarClientes() {
        return contaRepository.findAllByUsuarioPerfilOrderByUsuarioNomeAsc(PerfilUsuario.CLIENTE)
                .stream()
                .map(ClienteAdministrativoResponse::from)
                .toList();
    }

    public ContaAdministrativaResponse consultarConta(Long contaId) {
        return ContaAdministrativaResponse.from(buscarConta(contaId));
    }

    @Transactional
    public void bloquearConta(Long contaId) {
        Conta conta = buscarConta(contaId);
        conta.bloquear();
    }

    @Transactional
    public void desbloquearConta(Long contaId) {
        Conta conta = buscarConta(contaId);
        conta.desbloquear();
    }

    public List<ItemExtratoResponse> consultarMovimentacoes(Long contaId) {
        buscarConta(contaId);

        return transacaoRepository.findAllByContaIdOrderByDataDesc(contaId)
                .stream()
                .map(ItemExtratoResponse::from)
                .toList();
    }

    public List<CartaoAdministrativoResponse> consultarCartoes() {
        return cartaoRepository.findAllByOrderByDataSolicitacaoDesc()
                .stream()
                .map(CartaoAdministrativoResponse::from)
                .toList();
    }

    @Transactional
    public void bloquearCartao(Long cartaoId) {
        Cartao cartao = buscarCartao(cartaoId);
        cartao.bloquear();
    }

    @Transactional
    public void desbloquearCartao(Long cartaoId) {
        Cartao cartao = buscarCartao(cartaoId);
        cartao.desbloquear();
    }

    private Conta buscarConta(Long contaId) {
        return contaRepository.findById(contaId)
                .orElseThrow(() -> new ResourceNotFoundException("Conta nao encontrada"));
    }

    private Cartao buscarCartao(Long cartaoId) {
        return cartaoRepository.findById(cartaoId)
                .orElseThrow(() -> new ResourceNotFoundException("Cartao nao encontrado"));
    }
}
