package dev.kellyson.alexandriabank.conta;

import dev.kellyson.alexandriabank.usuario.Usuario;
import dev.kellyson.alexandriabank.exception.BadRequestException;
import dev.kellyson.alexandriabank.exception.BusinessRuleException;
import dev.kellyson.alexandriabank.exception.ConflictException;
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
            throw new BadRequestException("Valor de débito inválido.");
        }

        if (this.status != StatusConta.ATIVA) {
            throw new BusinessRuleException("Não é possível debitar de uma conta que não está ativa.");
        }

        if (this.saldo.compareTo(valor) < 0) {
            throw new BusinessRuleException("Saldo insuficiente para débito.");
        }

        this.saldo = this.saldo.subtract(valor);
    }

    public void creditar(BigDecimal valor) {
        if (valor == null || valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new BadRequestException("Valor de crédito inválido.");
        }

        if (this.status != StatusConta.ATIVA) {
            throw new BusinessRuleException("Não é possível creditar em uma conta que não está ativa.");
        }

        this.saldo = saldo.add(valor);
    }

    public void bloquear() {
        if (this.status == StatusConta.BLOQUEADA) {
            throw new ConflictException("A conta ja esta bloqueada");
        }

        if (this.status == StatusConta.ENCERRADA) {
            throw new BusinessRuleException("Nao e possivel bloquear uma conta encerrada");
        }

        this.status = StatusConta.BLOQUEADA;
    }

    public void desbloquear() {
        if (this.status == StatusConta.ATIVA) {
            throw new ConflictException("A conta ja esta ativa");
        }

        if (this.status == StatusConta.ENCERRADA) {
            throw new BusinessRuleException("Nao e possivel desbloquear uma conta encerrada");
        }

        this.status = StatusConta.ATIVA;
    }

}
