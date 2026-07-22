package dev.kellyson.alexandriabank.cliente.transacao.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record RealizarPixRequest(
        @NotNull(message = "A conta de destino é obrigatória")
        @Positive(message = "A conta de destino deve ser valida")
        Long idContaDestino,

        @NotNull(message = "O valor e obrigatório")
        @DecimalMin(value = "0.01", message = "O valor deve ser maior que zero")
        BigDecimal valor
) {
}
