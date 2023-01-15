package ru.yandex.practicum.javafilmorate.exceptions;

public class FilmDescriptionIsTooLongException extends RuntimeException{

    public FilmDescriptionIsTooLongException(String message){
        super(message);
    }
}
