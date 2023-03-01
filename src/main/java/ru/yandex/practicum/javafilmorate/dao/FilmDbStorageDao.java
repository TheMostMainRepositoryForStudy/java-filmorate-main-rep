package ru.yandex.practicum.javafilmorate.dao;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.javafilmorate.exceptions.EntityDoesNotExistException;
import ru.yandex.practicum.javafilmorate.model.Film;
import ru.yandex.practicum.javafilmorate.model.Mpa;
import ru.yandex.practicum.javafilmorate.storage.FilmStorage;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Duration;
import java.time.LocalDate;
import java.util.List;

@Slf4j
@Component
@Qualifier("filmStorageDb")
public class FilmDbStorageDao implements FilmStorage {

    @Autowired
    private final JdbcTemplate jdbcTemplate;
    @Autowired
    private final FilmGenreDao filmGenreDao;
    @Autowired
    private final LikeDao likeDao;

    @Autowired
    private final MpaDao mpaDao;


    public FilmDbStorageDao(JdbcTemplate jdbcTemplate, FilmGenreDao filmGenreDao, LikeDao likeDao, MpaDao mpaDao) {
        this.jdbcTemplate = jdbcTemplate;
        this.filmGenreDao = filmGenreDao;
        this.likeDao = likeDao;
        this.mpaDao = mpaDao;
    }


    @Override
    public Film addFilm(Film film) {
        String sqlQuery = "INSERT INTO FILM ( name, description, release_date, duration, mpa)" +
                          "VALUES (?,?,?,?,?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement stmt = connection.prepareStatement(sqlQuery, new String[]{"id"});
            stmt.setString(1, film.getName());
            stmt.setString(2, film.getDescription());
            stmt.setDate(3, Date.valueOf(film.getReleaseDate()));
            stmt.setInt(4, (int) film.getDuration().toMinutes());
            stmt.setInt(5, film.getMpa().getId());
            return stmt;
        }, keyHolder);
        long idKey = keyHolder.getKey().longValue();
        film.setId(idKey);

        return film;
    }

    @Override
    public Film getFilm(Long id) {
        String sql = "SELECT id, name, description, release_date, duration, mpa, rate " +
                     "FROM film " +
                     "WHERE id = ?";
        Film film = null;
        try {
            film = jdbcTemplate.queryForObject(sql,
                    (ResultSet rs, int rowNum) -> {
                        return makeFilm(rs);
                    },
                    id);
            log.info("Найден фильм: c id = {} названием = {}", film.getId(), film.getName());
            film.setLikes(likeDao.getFilmLikes(id));
            film.setGenres(filmGenreDao.getFilmGenre(id));
            film.setMpa(mpaDao.getMpaById(film.getMpa().getId()));
            return film;
        } catch (EmptyResultDataAccessException e) {
            log.debug("Фильм с идентификатором {} не найден.", id);
            throw new EntityDoesNotExistException(String.format("Фильм с идентификатором %d не найден.", id));
        }
    }

    @Override
    public Film removeFilm(Long id) {

        return null;
    }

    @Override
    public Film updateFilm(Film film) {

        String sql = "UPDATE FILM " +
                     "SET name = ?," +
                         "description = ?," +
                         "release_date = ?," +
                         "duration = ?," +
                         "mpa = ?,"+
                         "rate = ? " +
                     "WHERE id = ?";
        int updatedRows = jdbcTemplate.update(sql
                                            , film.getName()
                                            , film.getDescription()
                                            , Date.valueOf(film.getReleaseDate())
                                            , (int) film.getDuration().toMinutes()
                                            , film.getMpa().getId()
                                            , film.getRate()
                                            , film.getId());
        if(updatedRows == 0){
            log.debug("Фильм с идентификатором {} не найден.", film.getId());
            throw new EntityDoesNotExistException(
                    String.format("Фильм с идентификатором %d не найден.", film.getId()));
        } else{
            return film;
        }
    }

    @Override
    public List<Film> getAllFilms() {
        String sql = "SELECT id, name, description, release_date, duration, mpa, rate " +
                "FROM film ";
        List<Film> films =  jdbcTemplate.query(sql, (rs, rowNum) -> makeFilm(rs));
        films.forEach((film) -> {
            film.setLikes(likeDao.getFilmLikes(film.getId()));
            film.setGenres(filmGenreDao.getFilmGenre(film.getId()));
            film.setMpa(mpaDao.getMpaById(film.getMpa().getId()));
        });
        return films;
    }

    @Override
    public boolean doesFilmExist(long id) {
        return false;
    }

    private Film makeFilm( ResultSet rs) throws SQLException {
        long id = rs.getLong("id");
        String name = rs.getString("name");
        String description = rs.getString("description");
        LocalDate releaseDate = rs.getDate("release_date").toLocalDate();
        Duration duration = Duration.ofMinutes(rs.getInt("duration"));
        Mpa mpa = new Mpa(rs.getInt("mpa"));
        int rate = rs.getInt("rate");

        return Film.builder()
                    .id(id)
                    .name(name)
                    .description(description)
                    .releaseDate(releaseDate)
                    .duration(duration)
                    .mpa(mpa)
                    .rate(rate)
                    .build();
    }
}

