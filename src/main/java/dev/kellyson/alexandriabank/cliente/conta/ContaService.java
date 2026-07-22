package dev.kellyson.alexandriabank.cliente.conta;

import dev.kellyson.alexandriabank.cliente.conta.dto.AporteSimuladoRequest;
import dev.kellyson.alexandriabank.cliente.conta.dto.SaldoResponse;
import dev.kellyson.alexandriabank.exception.ResourceNotFoundException;
import dev.kellyson.alexandriabank.cliente.transacao.NaturezaTransacao;
import dev.kellyson.alexandriabank.cliente.transacao.TipoOperacao;
import dev.kellyson.alexandriabank.cliente.transacao.TransacaoService;
import dev.kellyson.alexandriabank.cliente.transacao.dto.ItemExtratoResponse;
import dev.kellyson.alexandriabank.usuario.Usuario;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
public class ContaService {

    private final ContaRepository contaRepository;
    private final TransacaoService transacaoService;

    public void abrirConta(Usuario usuario) {
        Conta novaConta = new Conta(usuario);

        contaRepository.save(novaConta);
    }

    public SaldoResponse consultarSaldo(Usuario usuario) {
        Conta conta = contaRepository.findByUsuarioId(usuario.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Conta não encontrada"));

        return new SaldoResponse(conta.getSaldo());
    }

    public List<ItemExtratoResponse> consultarExtrato(Usuario usuario) {
        return transacaoService.consultarExtratoConta(usuario);
    }

    @Transactional
    public void realizarAporteSimulado(Usuario usuario, AporteSimuladoRequest request) {
        Conta conta = contaRepository.findByUsuarioId(usuario.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Conta não encontrada"));

        conta.creditar(request.valor());

        transacaoService.registrarTransacao(
                conta,
                request.valor(),
                NaturezaTransacao.ENTRADA,
                TipoOperacao.APORTE_SIMULADO,
                "Aporte simulado"
        );
    }
}
