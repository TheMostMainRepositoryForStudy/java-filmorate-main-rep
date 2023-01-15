package ru.yandex.practicum.javafilmorate.exceptions;

public class FilmDoesNotExistException extends RuntimeException{

    public FilmDoesNotExistException(String message){
        super(message);
    }
}
