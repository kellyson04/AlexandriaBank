package dev.kellyson.alexandriabank.cliente.conta;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.kellyson.alexandriabank.usuario.PerfilUsuario;

import java.util.List;
import java.util.Optional;

public interface ContaRepository extends JpaRepository<Conta, Long> {

    Optional<Conta> findByUsuarioId(Long usuarioId);

    List<Conta> findAllByUsuarioPerfilOrderByUsuarioNomeAsc(PerfilUsuario perfil);
}
