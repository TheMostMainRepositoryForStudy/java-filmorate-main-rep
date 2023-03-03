package ru.yandex.practicum.javafilmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.javafilmorate.dao.GenreDao;
import ru.yandex.practicum.javafilmorate.model.Genre;

import java.util.List;

@Component
@RequiredArgsConstructor
public class GenreService {

    private final GenreDao genreDao;

    public List<Genre> getAllGenresFromDb() {
        return genreDao.getAll();
    }

    public Genre getGenreByIdFromDb(long id) {
        return genreDao.getGenreById(id);
    }
}
