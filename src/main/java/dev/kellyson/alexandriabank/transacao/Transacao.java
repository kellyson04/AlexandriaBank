package dev.kellyson.alexandriabank.transacao;

import dev.kellyson.alexandriabank.conta.Conta;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;

@NoArgsConstructor
@Getter
@Entity
@Table(name = "tb_transacoes")
public class Transacao {

    public Transacao(BigDecimal valor, TipoTransacao tipo, String descricao, Conta conta) {
        this.valor = valor;
        this.tipo = tipo;
        this.descricao = descricao;
        this.conta = conta;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "conta_id", nullable = false)
    private Conta conta;

    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal valor;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private TipoTransacao tipo;

    @Column(nullable = false)
    private Instant data;

    @Column(nullable = false, length = 255)
    private String descricao;

    @PrePersist
    void prePersist() {
        this.data = Instant.now();
    }

}
