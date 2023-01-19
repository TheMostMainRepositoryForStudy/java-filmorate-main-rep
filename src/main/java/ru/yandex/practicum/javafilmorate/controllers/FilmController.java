package ru.yandex.practicum.javafilmorate.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.javafilmorate.exceptions.*;
import ru.yandex.practicum.javafilmorate.model.Film;
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
    private int idToNewFilm = 1;
    private final HashMap<Integer, Film> films = new HashMap<>();

    private int generateFilmId() {
        int idToSet = idToNewFilm;
        idToNewFilm++;
        return idToSet;
    }

    @PostMapping
    public Film addNewFilm(@RequestBody @Valid Film film) {
        log.info("Получен запрос POST /films");
        System.out.println(film);
        if (validateFilm(film)) {
            // Проверяю, что в коллекции нет фильма с такими же названием,
            // описанием, датой релиза и длительностью
            if (films.containsValue(film)) {
                String exceptionMessage = "Фильм уже добавлен в библиотеку";
                log.warn("Ошибка при добавлении нового фильма. Сообщение исключения: {}",
                        exceptionMessage);
                throw new EntityAlreadyExistsException(exceptionMessage);
            }

            film.setId(generateFilmId());
            films.put(film.getId(), film);
            return film;

        }

        return null;
    }

    @PutMapping
    public Film updateFilm(@RequestBody @Valid Film film) {
        if (validateFilm(film)) {

            if (!films.containsKey(film.getId())) {
                String exceptionMessage = "Обновляемый фильм не существует";
                log.warn("Ошибка при обновлении существующего фильма. Сообщение исключения: {}", exceptionMessage);
                throw new EntityDoesNotExistException(exceptionMessage);
            }

            films.remove(film.getId(), film);
            films.put(film.getId(), film);
            return film;
        }

        return null;
    }

    @GetMapping
    public List<Film> getAll() {

        return new ArrayList<>(films.values());
    }

    public boolean validateFilm(Film film) {

        if (StringValidator.isNullOrEmpty(film.getName())) {
            String exceptionMessage = "Имя фильма не может быть "
                    + " не задано или пустой строкой";
            log.warn("Ошибка при валидации фильма. Сообщение исключения: {}", exceptionMessage);
            throw new FilmValidationException(exceptionMessage);
        }

        if (StringValidator.isLengthBiggerThanMaxLength(film.getDescription(), MAX_DESCRIPTION_LENGTH)) {
            String exceptionMessage = "Длина описания больше " + MAX_DESCRIPTION_LENGTH + " символов";
            log.warn("Ошибка при валидации фильма. Сообщение исключения: {}", exceptionMessage);
            throw new FilmValidationException(exceptionMessage);
        }

        if (LocalDateValidator.isDateTooOld(film.getReleaseDate(), THE_OLDEST_RELEASE_DATE)) {
            String exceptionMessage = "Слишком старая дата релиза."
                    + " Можно добавить фильмы с датой релиза после 28.12.1895";
            log.warn("Ошибка при валидации фильма. Сообщение исключения: {}", exceptionMessage);
            throw new FilmValidationException(exceptionMessage);
        }

        if (DurationValidator.isDurationNegativeOrZero(film.getDuration())) {
            String exceptionMessage = "Длительность фильма должна быть больше 0 сек.";
            log.warn("Ошибка при валидации фильма. Сообщение исключения: {}", exceptionMessage);
            throw new FilmValidationException(exceptionMessage);
        }

        return true;
    }
}
