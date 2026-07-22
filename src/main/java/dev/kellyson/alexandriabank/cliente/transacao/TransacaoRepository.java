package dev.kellyson.alexandriabank.cliente.transacao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransacaoRepository extends JpaRepository<Transacao, Long> {

    List<Transacao> findAllByContaIdOrderByDataDesc(Long contaId);

    List<Transacao> findAllByCartaoIdOrderByDataDesc(Long cartaoId);
}
