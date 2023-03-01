package ru.yandex.practicum.javafilmorate.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.javafilmorate.exceptions.EntityAlreadyExistsException;
import ru.yandex.practicum.javafilmorate.exceptions.EntityDoesNotExistException;
import ru.yandex.practicum.javafilmorate.model.Film;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
@Qualifier("filmStorage")
public class InMemoryFilmStorage implements FilmStorage {

    private final Map<Long,Film> films = new HashMap<>();

    private long idToNewFilm = 1;

    private long generateFilmId() {
        long idToSet = idToNewFilm;
        idToNewFilm++;
        return idToSet;
    }

    @Override
    public Film addFilm(Film film) {

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

    @Override
    public Film getFilm(Long id) {

        if(!films.containsKey(id)){
            String exceptionMessage = String.format("Фильм с запрашиваемым id = %d не существует", id);
            log.warn("Ошибка при запросе фильма. Сообщение исключения: {}",
                    exceptionMessage);
            throw new EntityDoesNotExistException(exceptionMessage);
        }
        return films.get(id);
    }

    @Override
    public Film removeFilm(Long id) {

        if(!films.containsKey(id)){
            String exceptionMessage = String.format("Фильм с запрашиваемым id = %d не существует", id);
            log.warn("Ошибка при запросе фильма. Сообщение исключения: {}",
                    exceptionMessage);
            throw new EntityDoesNotExistException(exceptionMessage);
        }

        return films.remove(id);
    }

    @Override
    public Film updateFilm(Film film) {

        if (!films.containsKey(film.getId())) {
            String exceptionMessage = "Обновляемый фильм не существует";
            log.warn("Ошибка при обновлении существующего фильма. Сообщение исключения: {}", exceptionMessage);
            throw new EntityDoesNotExistException(exceptionMessage);
        }

        films.remove(film.getId());
        films.put(film.getId(), film);
        return film;
    }

    @Override
    public List<Film> getAllFilms() {
        return new ArrayList<>(films.values());
    }

    @Override
    public boolean doesFilmExist(long id) {
        return films.containsKey(id);
    }
}
