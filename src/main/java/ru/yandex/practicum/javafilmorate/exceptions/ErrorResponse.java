package ru.yandex.practicum.javafilmorate.exceptions;

import java.util.Optional;

public class ErrorResponse {

    private final Optional<String> error;
    private final String description;


    public ErrorResponse(String error, String description) {
        this.error = Optional.of(String.format("Ошибка с %s", error));
        this.description = description;
    }

    public ErrorResponse(String description) {
        this.error = Optional.of("Error description is below:");
        this.description = description;
    }

    public String getError() {
        return error.orElse("Error description is below:");
    }

    public String getDescription() {
        return description;
    }
}
