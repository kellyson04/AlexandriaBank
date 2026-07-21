package dev.kellyson.alexandriabank.conta;

import dev.kellyson.alexandriabank.usuario.PerfilUsuario;
import dev.kellyson.alexandriabank.usuario.Usuario;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ContaTest {



    @Test
    void deveCreditarValorPositivo() {
        Usuario usuario = new Usuario(
                "Kellyson",
                "12345678900",
                "kellyson@email.com",
                "senha123",
                PerfilUsuario.CLIENTE
        );
        Conta conta = new Conta(usuario);


        BigDecimal valor = new BigDecimal("100.00");
        conta.creditar(valor);


        assertEquals(
                new BigDecimal("100.00"),
                conta.getSaldo()
        );
    }


}
