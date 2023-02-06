package ru.yandex.practicum.javafilmorate.exceptions;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InvalidPathVariableOrParameterException extends RuntimeException {

    private String param;

    public InvalidPathVariableOrParameterException(String param, String message) {
        super(message);
        this.param = param;
    }
}
