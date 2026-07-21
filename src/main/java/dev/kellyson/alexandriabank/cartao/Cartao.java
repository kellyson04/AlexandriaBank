package dev.kellyson.alexandriabank.cartao;

import dev.kellyson.alexandriabank.conta.Conta;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.LocalDate;

@NoArgsConstructor
@Getter
@Entity
@Table(name = "tb_cartoes")
public class Cartao {

    public Cartao(String numero, String nomeImpresso, LocalDate dataValidade, Conta conta) {
        this.numero = numero;
        this.nomeImpresso = nomeImpresso;
        this.dataValidade = dataValidade;
        this.status = StatusCartao.ATIVO;
        this.conta = conta;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(optional = false)
    @JoinColumn(name = "conta_id", nullable = false, unique = true)
    private Conta conta;

    @Column(nullable = false, unique = true, length = 16)
    private String numero;

    @Column(name = "nome_impresso", nullable = false, length = 120)
    private String nomeImpresso;

    @Column(name = "data_validade", nullable = false)
    private LocalDate dataValidade;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private StatusCartao status;

    @Column(name = "data_solicitacao", nullable = false, updatable = false)
    private Instant dataSolicitacao;

    @PrePersist
    void prePersist() {
        this.dataSolicitacao = Instant.now();
    }

    public void bloquear() {
        if (this.status == StatusCartao.BLOQUEADO) {
            throw new IllegalStateException("O cartao ja esta bloqueado");
        }

        this.status = StatusCartao.BLOQUEADO;
    }

    public void desbloquear() {
        if (this.status == StatusCartao.ATIVO) {
            throw new IllegalStateException("O cartao ja esta ativo");
        }

        this.status = StatusCartao.ATIVO;
    }

}
