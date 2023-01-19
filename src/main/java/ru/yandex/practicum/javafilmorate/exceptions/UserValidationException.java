package ru.yandex.practicum.javafilmorate.exceptions;

public class UserValidationException extends RuntimeException {

    public UserValidationException(String message) {
        super(message);
    }
}
