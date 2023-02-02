package ru.yandex.practicum.javafilmorate.exceptions;

public class InvalidPathVariableOrParameterException extends RuntimeException{

    String param;

    public InvalidPathVariableOrParameterException(String param, String message){
        super(message);
        this.param = param;
    }
}
