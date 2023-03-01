package ru.yandex.practicum.javafilmorate.dao;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.javafilmorate.model.Genre;

import java.util.List;

@Slf4j
@Component
public class FilmGenreDao {

    @Autowired
    private final JdbcTemplate jdbcTemplate;
    private final GenreDao genreDao;


    public FilmGenreDao(JdbcTemplate jdbcTemplate, GenreDao genreDao) {
        this.jdbcTemplate = jdbcTemplate;
        this.genreDao = genreDao;
    }

    public List<Genre> getFilmGenre(long id){
        String sql = "SELECT g.id, g.name " +
                     "FROM film_genre fg " +
                     "LEFT JOIN genre g ON  fg.genre_id = g.id " +
                     "WHERE fg.film_id = ?";
        return jdbcTemplate.query(sql, (rs, rowNum) -> genreDao.makeGenre(rs), id);
    }
}
