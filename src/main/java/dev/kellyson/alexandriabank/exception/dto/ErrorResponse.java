package dev.kellyson.alexandriabank.exception.dto;

import java.time.LocalDateTime;
import java.util.List;

public record ErrorResponse(
        int status,
        String error,
        String message,
        List<InvalidField> fields,
        LocalDateTime timestamp
) {
}
