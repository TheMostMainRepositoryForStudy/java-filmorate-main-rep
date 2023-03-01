package ru.yandex.practicum.javafilmorate.service;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.javafilmorate.dao.GenreDao;
import ru.yandex.practicum.javafilmorate.dao.MpaDao;
import ru.yandex.practicum.javafilmorate.model.Genre;
import ru.yandex.practicum.javafilmorate.model.Mpa;

import java.util.List;

@Component
public class GenreService {

    private final GenreDao genreDao;

    public GenreService(GenreDao genreDao) {
        this.genreDao = genreDao;
    }

    public List<Genre> getAllGenresFromDb() {
        return genreDao.getAll();
    }

    public Genre getGenreByIdFromDb(long id) {
        return genreDao.getGenreById(id);
    }
}
