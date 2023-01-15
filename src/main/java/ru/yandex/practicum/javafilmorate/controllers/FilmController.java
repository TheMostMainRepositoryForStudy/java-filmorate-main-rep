package ru.yandex.practicum.javafilmorate.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.javafilmorate.exceptions.*;
import ru.yandex.practicum.javafilmorate.model.Film;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.*;

@RestController
@RequestMapping("/films")
public class FilmController {

    public static final Logger log = LoggerFactory.getLogger(FilmController.class);

    private int idToNewFilm = 1;

    private int generateFilmId(){
        int idToSet = idToNewFilm;
        idToNewFilm++;
        return idToSet;
    }

    HashMap<Integer, Film> films = new HashMap<>();

    @PostMapping()
    public Film addNewFilm(@RequestBody @Valid Film film){
        log.info("Получен запрос POST /films");

        if(validateFilm(film)){
            // Проверяю, что в коллекции нет фильма с такими же названием,
            // описанием, датой релиза и длительностью
            if(films.containsValue(film)) {
                String exceptionMessage = "Фильм уже добавлен в библиотеку";
                log.warn("Ошибка при добавлении нового фильма. Сообщение исключения: {}",
                                                                                exceptionMessage);
                throw new FilmAlreadyExistException(exceptionMessage);
            }

            film.setId(generateFilmId());
            films.put(film.getId(), film);
            return film;

        }

        return null;
    }

    @PutMapping()
    public Film updateFilm(@RequestBody @Valid  Film film){
        if(validateFilm(film)){

            if(!films.containsKey(film.getId())) {
                String exceptionMessage = "Обновляемый фильм не существует";
                log.warn("Ошибка при обновлении существующего фильма. Сообщение исключения: {}", exceptionMessage);
                throw new FilmDoesNotExistException(exceptionMessage);
            }

            films.remove(film.getId(),film);
            films.put(film.getId(),film);
            return film;
        }

        return null;
    }

    @GetMapping
    public List<Film> getAll(){

        return new ArrayList<>(films.values());
    }

    public boolean validateFilm(Film film){

        if(film.getName() == null || film.getName().isEmpty()){
            String exceptionMessage = "Имя фильма не может быть "
                    + " не задано или пустой строкой";
            log.warn("Ошибка при валидации фильма. Сообщение исключения: {}", exceptionMessage);
            throw new FilmNameNullOrEmptyException(exceptionMessage);
        }

        if(film.getDescription().length() > 200){
            String exceptionMessage = "Длина описания больше 200 символов";
            log.warn("Ошибка при валидации фильма. Сообщение исключения: {}", exceptionMessage);
            throw new FilmDescriptionIsTooLongException(exceptionMessage);
        }

        LocalDate theOldestPossibleReleaseDate = LocalDate.of(1895,12,28);

        if(film.getReleaseDate().isBefore(theOldestPossibleReleaseDate)){
            String exceptionMessage = "Слишком старая дата релиза."
                    + " Можно добавить фильмы с датой релиза после 28.12.1895";
            log.warn("Ошибка при валидации фильма. Сообщение исключения: {}", exceptionMessage);
            throw new FilmReleaseDateException(exceptionMessage);
        }

        if( film.getDuration().toSeconds() <= 0){
            String exceptionMessage = "Длительность фильма должна быть больше 0 сек.";
            log.warn("Ошибка при валидации фильма. Сообщение исключения: {}", exceptionMessage);
            throw new FilmDurationIsNegativeValueException(exceptionMessage);
        }

        return true;
    }
}
