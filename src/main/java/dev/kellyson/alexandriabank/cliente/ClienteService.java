package dev.kellyson.alexandriabank.cliente;

import dev.kellyson.alexandriabank.cliente.dto.ClienteCriadoResponse;
import dev.kellyson.alexandriabank.cliente.dto.CriarClienteRequest;
import dev.kellyson.alexandriabank.cliente.conta.ContaService;
import dev.kellyson.alexandriabank.exception.ConflictException;
import dev.kellyson.alexandriabank.usuario.PerfilUsuario;
import dev.kellyson.alexandriabank.usuario.Usuario;
import dev.kellyson.alexandriabank.usuario.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ClienteService {

    private final UsuarioRepository usuarioRepository;
    private final ContaService contaService;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public ClienteCriadoResponse criarCliente(CriarClienteRequest clienteRequest) {

        String senhaCodificada = passwordEncoder.encode(clienteRequest.senha());

        Usuario novoCliente = new Usuario(
                clienteRequest.nome(),
                clienteRequest.cpf(),
                clienteRequest.email(),
                senhaCodificada,
                PerfilUsuario.CLIENTE
        );

        if (usuarioRepository.existsByCpf(novoCliente.getCpf())) {
            throw new ConflictException("CPF já cadastrado");
        }

        if (usuarioRepository.existsByEmail(novoCliente.getEmail())) {
            throw new ConflictException("Email já cadastrado");
        }

        usuarioRepository.save(novoCliente);

        contaService.abrirConta(novoCliente);

        return ClienteMapper.paraResposta(novoCliente);
    }

}
