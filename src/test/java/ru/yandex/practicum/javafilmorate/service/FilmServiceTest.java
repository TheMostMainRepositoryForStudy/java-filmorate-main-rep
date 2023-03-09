package ru.yandex.practicum.javafilmorate.service;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import ru.yandex.practicum.javafilmorate.dao.FilmDbStorageDao;
import ru.yandex.practicum.javafilmorate.dao.FilmGenreDao;
import ru.yandex.practicum.javafilmorate.dao.GenreDao;
import ru.yandex.practicum.javafilmorate.model.Film;
import ru.yandex.practicum.javafilmorate.model.FilmGenre;
import ru.yandex.practicum.javafilmorate.model.Genre;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class FilmServiceTest {

    private final FilmDbStorageDao filmDbStorageDao;

    private final FilmGenreDao filmGenreDao;

    private final GenreDao genreDao;

    @Test
    @Sql(scripts = "file:src/test/java/ru/yandex/practicum/javafilmorate/TestResources/schema2.sql"
            , executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "file:src/test/java/ru/yandex/practicum/javafilmorate/TestResources/data2.sql"
            , executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void shouldReturn() {

        List<Film> films = filmDbStorageDao.getAllFilms();
        List<FilmGenre> filmGenres = filmGenreDao.getAllFilmGenres();
        Map<Long, Genre> genres = genreDao.getAll().stream().collect(Collectors.toMap(Genre::getId, genre -> genre));
        Map<Long, List<Genre>> map = new HashMap<>();

        for(FilmGenre filmGenre : filmGenres){
            if(!map.containsKey(filmGenre.getFilmId())){
                map.put(filmGenre.getFilmId(), new ArrayList<>());
            }
            map.get(filmGenre.getFilmId()).add(genres.get(filmGenre.getGenreId()));
        }

        films.forEach(film -> film.setGenres(map.get(film.getId())));

        films.forEach(System.out::println);
    }

}