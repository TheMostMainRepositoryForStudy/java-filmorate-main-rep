package ru.yandex.practicum.javafilmorate.exceptions;

public class UserLoginIsEmptyOrBlankException extends RuntimeException{

    public UserLoginIsEmptyOrBlankException(String message){
        super(message);
    }
}
