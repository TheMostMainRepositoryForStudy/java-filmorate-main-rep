package ru.yandex.practicum.javafilmorate.exceptions;

public class UserDoesNotExistException extends RuntimeException{

    public UserDoesNotExistException(String message){
        super(message);
    }
}
