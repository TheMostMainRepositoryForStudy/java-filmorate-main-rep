package ru.yandex.practicum.javafilmorate.exceptions;


public class ErrorResponse {

    private final String error;
    private final String description;


    public ErrorResponse(String error, String description) {
        this.error = String.format("Ошибка с %s", error);
        this.description = description;
    }

    public ErrorResponse(String description) {
        this.error = "Error description is below:";
        this.description = description;
    }

    public String getError() {
        return error;
    }

    public String getDescription() {
        return description;
    }
}
