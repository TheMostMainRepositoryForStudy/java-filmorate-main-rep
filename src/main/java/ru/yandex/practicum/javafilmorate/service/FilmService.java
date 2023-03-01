package ru.yandex.practicum.javafilmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.javafilmorate.dao.LikeDao;
import ru.yandex.practicum.javafilmorate.exceptions.EntityAlreadyExistsException;
import ru.yandex.practicum.javafilmorate.exceptions.EntityDoesNotExistException;
import ru.yandex.practicum.javafilmorate.exceptions.InvalidPathVariableOrParameterException;
import ru.yandex.practicum.javafilmorate.model.Film;
import ru.yandex.practicum.javafilmorate.storage.FilmStorage;
import ru.yandex.practicum.javafilmorate.storage.UserStorage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class FilmService {
    private final FilmStorage filmStorage;
    private final LikeDao likeDao;

    private final UserStorage userStorage;

    public FilmService(@Qualifier("filmStorageDb") FilmStorage filmStorage
                        , LikeDao likeDao, @Qualifier("userStorageDb") UserStorage userStorage) {
        this.filmStorage = filmStorage;
        this.likeDao = likeDao;
        this.userStorage = userStorage;
    }

    public Film addNewFilmToStorage(Film film) {
        return filmStorage.addFilm(film);
    }

    public Film updateFilmInStorage(Film film) {
        return filmStorage.updateFilm(film);
    }

    public List<Film> getAllFilmsFromStorage() {
        return filmStorage.getAllFilms();
    }

    public boolean doesFilmExistInStorage(long id) {
        return filmStorage.doesFilmExist(id);
    }

    public Film getFilmFromStorage(long id) {
        return filmStorage.getFilm(id);
    }

    public void likeFilmInStorage(long id, long userId) {
        likeDao.putLike(id, userId);
    }

    public void deleteLikeFilmInStorage(long id, long userId) {
        likeDao.deleteLike(id,userId);
    }

    public List<Film> getMostLikedFilmsFromStorage(long count) {
        if (filmStorage.getAllFilms().isEmpty()) {
            return filmStorage.getAllFilms();
        }

        return filmStorage.getAllFilms()
                .stream()
                .sorted((o1, o2) -> o2.getRate() - o1.getRate())
                .limit(count)
                .collect(Collectors.toList());
    }

    private void checkFilmExistence(long id) {

        if (id < 1) {
            String exceptionMessage = String.format("Фильм с id = %d не может существовать", id);
            log.warn("Ошибка при запросе фильма. Сообщение исключения: {}",
                    exceptionMessage);
            throw new InvalidPathVariableOrParameterException("id", exceptionMessage);
        }

        if (!doesFilmExistInStorage(id)) {
            String exceptionMessage = String.format("Фильм с  id = %d не существует", id);
            log.warn("Ошибка при запросе фильма. Сообщение исключения: {}",
                    exceptionMessage);
            throw new EntityDoesNotExistException(exceptionMessage);
        }
    }
}
