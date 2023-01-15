package ru.yandex.practicum.javafilmorate.exceptions;

public class FilmNameNullOrEmptyException extends RuntimeException{

    public FilmNameNullOrEmptyException(String message){
        super(message);
    }
}
