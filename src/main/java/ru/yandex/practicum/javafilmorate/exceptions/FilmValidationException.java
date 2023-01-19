package ru.yandex.practicum.javafilmorate.exceptions;

public class FilmValidationException extends RuntimeException{

    public FilmValidationException(String message){
        super(message);
    }
}
