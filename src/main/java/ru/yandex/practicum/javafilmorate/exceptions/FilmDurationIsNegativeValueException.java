package ru.yandex.practicum.javafilmorate.exceptions;

public class FilmDurationIsNegativeValueException extends RuntimeException{

    public FilmDurationIsNegativeValueException(String message){
        super(message);
    }
}
