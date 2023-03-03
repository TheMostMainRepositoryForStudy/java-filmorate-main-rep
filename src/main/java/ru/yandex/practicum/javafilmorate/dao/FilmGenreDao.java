package ru.yandex.practicum.javafilmorate.dao;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.javafilmorate.model.Film;
import ru.yandex.practicum.javafilmorate.model.Genre;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class FilmGenreDao {

    private final JdbcTemplate jdbcTemplate;
    private final GenreDao genreDao;

    public List<Genre> getFilmGenre(long id){
        String sql = "SELECT g.id, g.name " +
                     "FROM film_genre fg " +
                     "LEFT JOIN genre g ON  fg.genre_id = g.id " +
                     "WHERE fg.film_id = ?";
        return jdbcTemplate.query(sql, (rs, rowNum) -> genreDao.makeGenre(rs), id);
    }
    public Film insertFilmGenre(Film film){

        String sql = "MERGE INTO FILM_GENRE fg USING (VALUES (?,?)) S(film, genre)\n" +
                     "ON fg.FILM_ID = S.film AND fg.GENRE_ID = S.genre \n" +
                     "WHEN NOT MATCHED THEN INSERT VALUES ( S.film, S.genre)";

        List<Genre> uniqGenres = film.getGenres().stream().distinct().collect(Collectors.toList());

        for( Genre genre: uniqGenres){
                jdbcTemplate.update(sql
                        , film.getId(), genre.getId());
        }

        film.setGenres(uniqGenres);
        return film;
    }

    public void deleteAllFilmGenresByFilmId(long filmId ){

        String sql = "DELETE FROM FILM_GENRE " +
                     "WHERE film_id = ?";
        jdbcTemplate.update(sql,filmId);
    }

}
