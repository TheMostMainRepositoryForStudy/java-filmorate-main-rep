package ru.yandex.practicum.javafilmorate.dao;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import ru.yandex.practicum.javafilmorate.model.Film;
import ru.yandex.practicum.javafilmorate.model.Mpa;

import java.time.Duration;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class FilmDbStorageDaoTest {
    private final FilmDbStorageDao filmStorage;

    @Test
    @Sql(scripts = "file:src/test/java/ru/yandex/practicum/javafilmorate/TestResources/schema2.sql"
            , executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "file:src/test/java/ru/yandex/practicum/javafilmorate/TestResources/data2.sql"
            , executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void shouldReturnFilm1() {


        Film filmExpected = Film.builder().id(1)
                .name("Titanic")
                .description("Test description")
                .duration(Duration.ofMinutes(90))
                .releaseDate(LocalDate.of(1997, 1, 27))
                .mpa(new Mpa(1, "G"))
                .build();

        Film filmActual = filmStorage.getFilm(1L);

        assertEquals(filmExpected.getId(),filmActual.getId());
        assertEquals(filmExpected.getName(),filmActual.getName());
        assertEquals(filmExpected.getDescription(),filmActual.getDescription());
        assertEquals(filmExpected.getReleaseDate(),filmActual.getReleaseDate());
        assertEquals(filmExpected.getMpa().getId(),filmActual.getMpa().getId());
    }

    @Test
    @Sql(scripts = "file:src/test/java/ru/yandex/practicum/javafilmorate/TestResources/schema2.sql"
            , executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "file:src/test/java/ru/yandex/practicum/javafilmorate/TestResources/data2.sql"
            , executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void shouldReturnFilm4() {

        Film filmToInsert= Film.builder().id(4)
                                .name("Titanic4")
                                .description("Test description4")
                                .duration(Duration.ofMinutes(90))
                                .releaseDate(LocalDate.of(1997, 1, 27))
                                .mpa(new Mpa(1, "G"))
                                .build();

        Film filmExpected = Film.builder().id(4)
                                .name("Titanic4")
                                .description("Test description4")
                                .duration(Duration.ofMinutes(90))
                                .releaseDate(LocalDate.of(1997, 1, 27))
                                .mpa(new Mpa(1, "G"))
                                .build();

        Film filmActual = filmStorage.addFilm(filmToInsert);

        assertEquals(filmExpected.getId(),filmActual.getId());
        assertEquals(filmExpected.getName(),filmActual.getName());
        assertEquals(filmExpected.getDescription(),filmActual.getDescription());
        assertEquals(filmExpected.getReleaseDate(),filmActual.getReleaseDate());
        assertEquals(filmExpected.getMpa(),filmActual.getMpa());
    }
    @Test
    @Sql(scripts = "file:src/test/java/ru/yandex/practicum/javafilmorate/TestResources/schema2.sql"
            , executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "file:src/test/java/ru/yandex/practicum/javafilmorate/TestResources/data2.sql"
            , executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void shouldReturnFilms() {

        Film film1Expected = Film.builder().id(1)
                .name("Titanic")
                .description("Test description")
                .duration(Duration.ofMinutes(90))
                .releaseDate(LocalDate.of(1997, 1, 27))
                .mpa(new Mpa(1, "G"))
                .build();
        Film film2Expected = Film.builder().id(2)
                .name("Titanic2")
                .description("Test description2")
                .duration(Duration.ofMinutes(90))
                .releaseDate(LocalDate.of(1997, 1, 27))
                .mpa(new Mpa(1, "G"))
                .build();
        Film film3Expected = Film.builder().id(3)
                .name("Titanic23")
                .description("Test description23")
                .duration(Duration.ofMinutes(90))
                .releaseDate(LocalDate.of(1997, 1, 27))
                .mpa(new Mpa(1, "G"))
                .build();

        List<Film> actualList = filmStorage.getAllFilms();

        assertEquals(film1Expected.getId(),actualList.get(0).getId());
        assertEquals(film1Expected.getName(),actualList.get(0).getName());
        assertEquals(film1Expected.getDescription(),actualList.get(0).getDescription());
        assertEquals(film1Expected.getReleaseDate(),actualList.get(0).getReleaseDate());
        assertEquals(film1Expected.getMpa().getId(),actualList.get(0).getMpa().getId());

        assertEquals(film2Expected.getId(),actualList.get(1).getId());
        assertEquals(film2Expected.getName(),actualList.get(1).getName());
        assertEquals(film2Expected.getDescription(),actualList.get(1).getDescription());
        assertEquals(film2Expected.getReleaseDate(),actualList.get(1).getReleaseDate());
        assertEquals(film2Expected.getMpa().getId(),actualList.get(1).getMpa().getId());

        assertEquals(film3Expected.getId(),actualList.get(2).getId());
        assertEquals(film3Expected.getName(),actualList.get(2).getName());
        assertEquals(film3Expected.getDescription(),actualList.get(2).getDescription());
        assertEquals(film3Expected.getReleaseDate(),actualList.get(2).getReleaseDate());
        assertEquals(film3Expected.getMpa().getId(),actualList.get(2).getMpa().getId());
    }

    @Test
    @Sql(scripts = "file:src/test/java/ru/yandex/practicum/javafilmorate/TestResources/schema2.sql"
                                        , executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "file:src/test/java/ru/yandex/practicum/javafilmorate/TestResources/data2.sql"
                                        , executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void shouldReturnUpdateFilm3() {

        Film film3Expected = Film.builder().id(3)
                .name("Titanic23Upd")
                .description("Test description23Upd")
                .duration(Duration.ofMinutes(90))
                .releaseDate(LocalDate.of(1997, 1, 27))
                .mpa(new Mpa(1, "G"))
                .build();

        filmStorage.updateFilm(film3Expected);
        Film actualFilm = filmStorage.getFilm(3L);

        assertEquals(film3Expected.getId(),actualFilm.getId());
        assertEquals(film3Expected.getName(),actualFilm.getName());
        assertEquals(film3Expected.getDescription(),actualFilm.getDescription());
        assertEquals(film3Expected.getReleaseDate(),actualFilm.getReleaseDate());
        assertEquals(film3Expected.getMpa().getId(),actualFilm.getMpa().getId());
    }

    @Test
    @Sql(scripts = "file:src/test/java/ru/yandex/practicum/javafilmorate/TestResources/schema2.sql"
            , executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "file:src/test/java/ru/yandex/practicum/javafilmorate/TestResources/data2.sql"
            , executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void shouldReturnMostLikedFriends_Film1() {

        Film filmExpected = Film.builder().id(1)
                .name("Titanic")
                .description("Test description")
                .duration(Duration.ofMinutes(90))
                .releaseDate(LocalDate.of(1997, 1, 27))
                .mpa(new Mpa(1, "G"))
                .build();

        Film filmActual = filmStorage.getMostLikedFilms(1).get(0);

        assertEquals(filmExpected.getId(),filmActual.getId());
        assertEquals(filmExpected.getName(),filmActual.getName());
        assertEquals(filmExpected.getDescription(),filmActual.getDescription());
        assertEquals(filmExpected.getReleaseDate(),filmActual.getReleaseDate());
        assertEquals(filmExpected.getMpa().getId(),filmActual.getMpa().getId());
    }
}