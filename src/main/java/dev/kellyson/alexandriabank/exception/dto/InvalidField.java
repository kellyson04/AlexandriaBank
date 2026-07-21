package dev.kellyson.alexandriabank.exception.dto;

public record InvalidField(
        String field,
        String message
) {
}
