package ru.yandex.practicum.javafilmorate.exceptions;

public class UserEmailNullOrEmptyException extends RuntimeException{

    public UserEmailNullOrEmptyException(String message){
        super(message);
    }
}
