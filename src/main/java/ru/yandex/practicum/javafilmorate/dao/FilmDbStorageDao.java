package ru.yandex.practicum.javafilmorate.dao;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
import java.util.Objects;

@Slf4j
@Component
@Qualifier("filmStorageDb")
@RequiredArgsConstructor
public class FilmDbStorageDao implements FilmStorage {


    private final JdbcTemplate jdbcTemplate;

    private final FilmGenreDao filmGenreDao;

    private final LikeDao likeDao;

    private final MpaDao mpaDao;

    @Override
    public Film addFilm(Film film) {
        String sqlQuery = "INSERT INTO FILM ( name, description, release_date, duration, mpa, rate, LIKES_AMOUNT) " +
                          "VALUES (?,?,?,?,?,?,?)";
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection -> {
                PreparedStatement stmt = connection.prepareStatement(sqlQuery, new String[]{"id"});
                stmt.setString(1, film.getName());
                stmt.setString(2, film.getDescription());
                stmt.setDate(3, Date.valueOf(film.getReleaseDate()));
                stmt.setInt(4, (int) film.getDuration().toSeconds());
                stmt.setInt(5, film.getMpa().getId());
                stmt.setInt(6, film.getRate());
                stmt.setInt(7, 0);
                return stmt;
            }, keyHolder);
            long idKey = Objects.requireNonNull(keyHolder.getKey()).longValue();
            film.setId(idKey);
            if(film.getGenres() == null || film.getGenres().isEmpty()){
                return film;
            }
                filmGenreDao.insertFilmGenre(film);
            return film;
    }

    @Override
    public Film getFilm(Long id) {
        String sql = "SELECT id, name, description, release_date, duration, mpa, rate, LIKES_AMOUNT\n" +
                     "FROM film " +
                     "WHERE id = ?";
        Film film;
        try {
            film = jdbcTemplate.queryForObject(sql,
                    (ResultSet rs, int rowNum) -> makeFilm(rs),
                    id);
            assert film != null;
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
        Film film = getFilm(id);
        String sql = "DELETE FROM FILM \n" +
                     "WHERE id = ?";
        jdbcTemplate.update(sql, id);
        return film;
    }

    @Override
    public Film updateFilm(Film film) {
        String sql = "UPDATE FILM " +
                     "SET name = ?, " +
                         "description = ?, " +
                         "release_date = ?, " +
                         "duration = ?, " +
                         "mpa = ?, "+
                         "rate = ? " +
                     " WHERE id = ?";
        int updatedRows = jdbcTemplate.update(sql
                                            , film.getName()
                                            , film.getDescription()
                                            , Date.valueOf(film.getReleaseDate())
                                            , (int) film.getDuration().toSeconds()
                                            , film.getMpa().getId()
                                            , film.getRate()
                                            , film.getId());
        if(updatedRows == 0){
            log.debug("Фильм с идентификатором {} не найден.", film.getId());
            throw new EntityDoesNotExistException(
                    String.format("Фильм с идентификатором %d не найден.", film.getId()));
        } else{
            filmGenreDao.deleteAllFilmGenresByFilmId(film.getId());
            if(film.getGenres() == null || film.getGenres().isEmpty()){
                return film;
            }
            System.out.println(film);
            return filmGenreDao.insertFilmGenre(film);
        }
    }

    @Override
    public List<Film> getAllFilms() {
        String sql = "SELECT id, name, description, release_date, duration, mpa ,rate , LIKES_AMOUNT \n" +
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
        Film film = getFilm(id);
        return film != null ;
    }

    private Film makeFilm( ResultSet rs) throws SQLException {
        long id = rs.getLong("id");
        String name = rs.getString("name");
        String description = rs.getString("description");
        LocalDate releaseDate = rs.getDate("release_date").toLocalDate();
        Duration duration = Duration.ofSeconds(rs.getInt("duration"));
        Mpa mpa = new Mpa(rs.getInt("mpa"));
        int rate = rs.getInt("rate");
        int likesAmount = rs.getInt("LIKES_AMOUNT");

        return Film.builder()
                    .id(id)
                    .name(name)
                    .description(description)
                    .releaseDate(releaseDate)
                    .duration(duration)
                    .rate(rate)
                    .mpa(mpa)
                    .likesAmount(likesAmount)
                    .build();
    }
    @Override
    public List<Film> getMostLikedFilms(int limit) {
        String sql = String.format(
                "SELECT id, name, description, release_date, duration, mpa, rate, LIKES_AMOUNT \n" +
                "FROM FILM\n" +
                "ORDER BY LIKES_AMOUNT DESC\n" +
                "LIMIT %d", limit
                );
        List<Film> films = jdbcTemplate.query(sql, (rs, rowNum) ->  makeFilm(rs));
        films.forEach((film) -> {
            film.setLikes(likeDao.getFilmLikes(film.getId()));
            film.setGenres(filmGenreDao.getFilmGenre(film.getId()));
            film.setMpa(mpaDao.getMpaById(film.getMpa().getId()));
        });
        return films;
    }
}

