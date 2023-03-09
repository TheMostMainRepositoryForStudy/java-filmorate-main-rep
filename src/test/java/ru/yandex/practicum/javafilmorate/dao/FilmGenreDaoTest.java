package ru.yandex.practicum.javafilmorate.dao;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import ru.yandex.practicum.javafilmorate.model.FilmGenre;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)

class FilmGenreDaoTest {

    private final FilmGenreDao filmGenreDao;

    @Test
    @Sql(scripts = "file:src/test/java/ru/yandex/practicum/javafilmorate/TestResources/schema2.sql"
            , executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "file:src/test/java/ru/yandex/practicum/javafilmorate/TestResources/data2.sql"
            , executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void shouldReturnAllFilmGenreList(){

        List<FilmGenre> result = filmGenreDao.getAllFilmGenres();
        assertEquals(3,result.size());
    }
}