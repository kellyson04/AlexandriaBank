package dev.kellyson.alexandriabank.conta;

import dev.kellyson.alexandriabank.usuario.Usuario;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;

@NoArgsConstructor
@Getter
@Entity
@Table(name = "tb_contas")
public class Conta {

    public Conta(Usuario usuario) {
        this.usuario = usuario;
        this.saldo = BigDecimal.ZERO;
        this.status = StatusConta.ATIVA;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(optional = false)
    @JoinColumn(name = "usuario_id", nullable = false, unique = true)
    private Usuario usuario;

    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal saldo;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusConta status;

    @Column(name = "criada_em",nullable = false,updatable = false)
    private Instant criadaEm;

    @PrePersist
    void prePersist() {
        this.criadaEm = Instant.now();
    }

    public void debitar(BigDecimal valor) {
        if (valor == null || valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Valor de débito inválido.");
        }

        if (this.status != StatusConta.ATIVA) {
            throw new IllegalStateException("Não é possível debitar de uma conta que não está ativa.");
        }

        if (this.saldo.compareTo(valor) < 0) {
            throw new IllegalStateException("Saldo insuficiente para débito.");
        }

        this.saldo = this.saldo.subtract(valor);
    }

    public void creditar(BigDecimal valor) {
        if (valor == null || valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Valor de débito inválido.");
        }

        if (this.status != StatusConta.ATIVA) {
            throw new IllegalStateException("Não é possível debitar de uma conta que não está ativa.");
        }

        this.saldo = saldo.add(valor);
    }

}
