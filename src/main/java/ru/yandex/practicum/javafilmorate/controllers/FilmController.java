package ru.yandex.practicum.javafilmorate.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.javafilmorate.exceptions.*;
import ru.yandex.practicum.javafilmorate.model.Film;
import ru.yandex.practicum.javafilmorate.service.FilmService;
import ru.yandex.practicum.javafilmorate.storage.FilmStorage;
import ru.yandex.practicum.javafilmorate.util.DurationValidator;
import ru.yandex.practicum.javafilmorate.util.LocalDateValidator;
import ru.yandex.practicum.javafilmorate.util.StringValidator;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.*;

@RestController
@RequestMapping("/films")
@Slf4j
public class FilmController {

    private final static int MAX_DESCRIPTION_LENGTH = 200;
    private final static LocalDate THE_OLDEST_RELEASE_DATE = LocalDate.of(1895, 12, 28);

    private final FilmStorage filmStorage;
    private final FilmService filmService;


    public FilmController(FilmStorage filmStorage, FilmService filmService) {
        this.filmStorage = filmStorage;
        this.filmService = filmService;
    }
    @PostMapping
    public Film add(@RequestBody @Valid Film film) {
        log.info("Получен запрос 'POST /films'");
        validateFilm(film);
        return filmService.addNewFilmToStorage(film);
    }

    @PutMapping
    public Film update(@RequestBody @Valid Film film) {
        log.info("Получен запрос 'PUT /films'");
        validateFilm(film);
        return filmService.updateFilmInStorage(film);
    }

    @PutMapping("/{id}/like/{userId}")
    public void likeFilm(@PathVariable long id, @PathVariable long userId) {
        log.info( String.format("Получен запрос 'PUT /films/%d/like/%d", id, userId));
        checkFilmExistence(id);
        filmService.likeFilmInStorage(id, userId);
    }

    @DeleteMapping("/{id}/like/{userId}")
    public void deleteLikeFromFilm(@PathVariable long id, @PathVariable long userId) {
        log.info( String.format("Получен запрос 'DELETE /films/%d/like/%d", id, userId));
        checkFilmExistence(id);
        filmService.deleteLikeFilmInStorage(id, userId);
    }


    @GetMapping("/{id}")
    public Film getFilm(@PathVariable long id) {
        log.info( String.format("Получен запрос 'GET /films/%d'", id));
        checkFilmExistence(id);
        return filmService.getFilmFromStorage(id);
    }

    @GetMapping
    public List<Film> getAll() {
        log.info("Получен запрос 'GET /films'");
        return filmService.getAllFilmsFromStorage();
    }

    @GetMapping("/popular")
    public List<Film> getMostLikedFilms(@RequestParam(defaultValue = "10") long count) {
        log.info(String.format("Получен запрос 'GET /films/popular?count=%d'", count));
        return filmService.getMostLikedFilmsFromStorage(count);
    }

    private void checkFilmExistence(long id){

        if(id < 1){
            String exceptionMessage = String.format("Фильм с id = %d не может существовать", id);
            log.warn("Ошибка при запросе фильма. Сообщение исключения: {}",
                    exceptionMessage);
            throw new InvalidPathVariableOrParameterException("id", exceptionMessage);
        }

        if(!filmService.doesFilmExistInStorage(id)){
            String exceptionMessage = String.format("Фильм с  id = %d не существует", id);
            log.warn("Ошибка при запросе фильма. Сообщение исключения: {}",
                    exceptionMessage);
            throw new EntityDoesNotExistException( exceptionMessage);
        }
    }

    private void validateFilm(Film film) {

        if (StringValidator.isNullOrEmpty(film.getName())) {
            String exceptionMessage = "Имя фильма не может быть "
                    + " не задано или пустой строкой";
            log.warn("Ошибка при валидации фильма. Сообщение исключения: {}", exceptionMessage);
            throw new ValidationException(exceptionMessage);
        }

        if (StringValidator.isLengthBiggerThanMaxLength(film.getDescription(), MAX_DESCRIPTION_LENGTH)) {
            String exceptionMessage = "Длина описания больше " + MAX_DESCRIPTION_LENGTH + " символов";
            log.warn("Ошибка при валидации фильма. Сообщение исключения: {}", exceptionMessage);
            throw new ValidationException(exceptionMessage);
        }

        if (LocalDateValidator.isDateTooOld(film.getReleaseDate(), THE_OLDEST_RELEASE_DATE)) {
            String exceptionMessage = "Слишком старая дата релиза."
                    + " Можно добавить фильмы с датой релиза после 28.12.1895";
            log.warn("Ошибка при валидации фильма. Сообщение исключения: {}", exceptionMessage);
            throw new ValidationException(exceptionMessage);
        }

        if (DurationValidator.isDurationNegativeOrZero(film.getDuration())) {
            String exceptionMessage = "Длительность фильма должна быть больше 0 сек.";
            log.warn("Ошибка при валидации фильма. Сообщение исключения: {}", exceptionMessage);
            throw new ValidationException(exceptionMessage);
        }
    }

    public FilmStorage getFilmStorage() {
        return filmStorage;
    }
}
