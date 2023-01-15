package ru.yandex.practicum.javafilmorate.exceptions;

public class UserEmailIsIncorrectException extends RuntimeException{

    public UserEmailIsIncorrectException(String message){
        super(message);
    }
}
