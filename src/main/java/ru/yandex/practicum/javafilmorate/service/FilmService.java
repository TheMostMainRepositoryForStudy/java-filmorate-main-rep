package ru.yandex.practicum.javafilmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.javafilmorate.dao.LikeDao;
import ru.yandex.practicum.javafilmorate.model.Film;
import ru.yandex.practicum.javafilmorate.storage.FilmStorage;

import java.util.List;

@Service
@Slf4j
public class FilmService {
    private final FilmStorage filmStorage;
    private final LikeDao likeDao;


    public FilmService(@Qualifier("filmStorageDb") FilmStorage filmStorage
                        , LikeDao likeDao) {
        this.filmStorage = filmStorage;
        this.likeDao = likeDao;
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

    public Film getFilmFromStorage(long id) {
        return filmStorage.getFilm(id);
    }

    public void likeFilmInStorage(long id, long userId) {
        likeDao.putLike(id, userId);
    }

    public void deleteLikeFilmInStorage(long id, long userId) {
        likeDao.deleteLike(id,userId);
    }

    public List<Film> getMostLikedFilmsFromStorage(int count) {
        return filmStorage.getMostLikedFilms(count);
    }
}
