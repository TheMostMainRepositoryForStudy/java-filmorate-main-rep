package ru.yandex.practicum.javafilmorate.exceptions;

public class UserAlreadyExistsException extends RuntimeException{


    public UserAlreadyExistsException(String message) {
        super(message);
    }
}
