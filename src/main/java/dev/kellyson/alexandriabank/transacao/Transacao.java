package dev.kellyson.alexandriabank.transacao;

import dev.kellyson.alexandriabank.cartao.Cartao;
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

    public Transacao(Conta conta, BigDecimal valor, NaturezaTransacao natureza,
                     TipoOperacao tipoOperacao, String descricao) {
        this.conta = conta;
        this.valor = valor;
        this.natureza = natureza;
        this.tipoOperacao = tipoOperacao;
        this.descricao = descricao;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "conta_id", nullable = false)
    private Conta conta;

    @ManyToOne
    @JoinColumn(name = "cartao_id")
    private Cartao cartao;

    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal valor;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private NaturezaTransacao natureza;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_operacao", nullable = false, length = 30)
    private TipoOperacao tipoOperacao;

    @Column(nullable = false)
    private Instant data;

    @Column(nullable = false, length = 255)
    private String descricao;

    @PrePersist
    void prePersist() {
        this.data = Instant.now();
    }

}
