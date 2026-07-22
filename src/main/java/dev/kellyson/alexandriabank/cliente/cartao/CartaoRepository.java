package dev.kellyson.alexandriabank.cliente.cartao;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartaoRepository extends JpaRepository<Cartao, Long> {

    boolean existsByContaId(Long contaId);

    boolean existsByNumero(String numero);

    Optional<Cartao> findByContaUsuarioId(Long usuarioId);

    List<Cartao> findAllByOrderByDataSolicitacaoDesc();
}
