package dev.kellyson.alexandriabank.cliente.dto;

import java.time.Instant;

public record ClienteCriadoResponse(
        Long id,
        String nome,
        String email,
        Instant criadoEm
) {
}
