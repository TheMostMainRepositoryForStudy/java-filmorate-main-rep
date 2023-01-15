package ru.yandex.practicum.javafilmorate.exceptions;

public class FilmAlreadyExistException extends RuntimeException{

    public FilmAlreadyExistException(String message){
        super(message);
    }
}
