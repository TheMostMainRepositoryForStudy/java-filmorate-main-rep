package ru.yandex.practicum.javafilmorate.controllers;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.javafilmorate.exceptions.FilmDescriptionIsTooLongException;
import ru.yandex.practicum.javafilmorate.exceptions.FilmDurationIsNegativeValueException;
import ru.yandex.practicum.javafilmorate.exceptions.FilmNameNullOrEmptyException;
import ru.yandex.practicum.javafilmorate.exceptions.FilmReleaseDateException;
import ru.yandex.practicum.javafilmorate.model.Film;

import java.time.Duration;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class FilmControllerTest {

    private static Film titanicWithoutName;
    private static Film titanicWithTooLongDescription;
    private static Film titanicWithNegativeDuration;
    private static Film titanicWithTooOldReleaseDate;
    public static FilmController filmController;

    @BeforeAll
    static void beforeAll(){

        filmController = new FilmController();

        titanicWithoutName = Film.builder().id(1)
                                            .description("Test description")
                                            .duration(Duration.ofMinutes(90))
                                            .releaseDate(LocalDate.of(1997, 1, 23))
                                            .build();

        String tooLongDescription = "Test description Test description Test description" +
                " Test description Test description Test description" +
                "Test description Test description Test description" +
                "Test description Test description Test description" +
                "Test description Test description Test description";

        titanicWithTooLongDescription = Film.builder().id(1)
                .name("Titanic")
                .description(tooLongDescription)
                .duration(Duration.ofMinutes(90))
                .releaseDate(LocalDate.of(1997, 1, 23))
                .build();

        titanicWithNegativeDuration = Film.builder().id(1)
                .name("Titanic")
                .description("Test Description")
                .duration(Duration.ofMinutes(-90))
                .releaseDate(LocalDate.of(1997, 1, 23))
                .build();

        titanicWithTooOldReleaseDate = Film.builder().id(1)
                .name("Titanic")
                .description("Test Description")
                .duration(Duration.ofMinutes(90))
                .releaseDate(LocalDate.of(1797, 1, 23))
                .build();

    }

    @Test
    public void shouldReturnFilmNameNullOrEmptyException(){

        FilmNameNullOrEmptyException ex =
                assertThrows(FilmNameNullOrEmptyException.class,
                        () -> filmController.validateFilm(titanicWithoutName));

        String exceptionMessage = "Имя фильма не может быть "
                + " не задано или пустой строкой";
        assertEquals(exceptionMessage, ex.getMessage());
    }

    @Test
    public void shouldReturnFilmDescriptionTooLongException(){

        FilmDescriptionIsTooLongException ex =
                assertThrows(FilmDescriptionIsTooLongException.class,
                        () -> filmController.validateFilm(titanicWithTooLongDescription));

        String exceptionMessage = "Длина описания больше 200 символов";
        assertEquals(exceptionMessage, ex.getMessage());
    }

    @Test
    public void shouldReturnFilmDurationIsNegativeValueException(){

        FilmDurationIsNegativeValueException ex =
                assertThrows(FilmDurationIsNegativeValueException.class,
                        () -> filmController.validateFilm(titanicWithNegativeDuration));

        String exceptionMessage = "Длительность фильма должна быть больше 0 сек.";
        assertEquals(exceptionMessage, ex.getMessage());
    }

    @Test
    public void shouldReturnFilmReleaseDateException(){

        FilmReleaseDateException ex =
                assertThrows(FilmReleaseDateException.class,
                        () -> filmController.validateFilm(titanicWithTooOldReleaseDate));

        String exceptionMessage = "Слишком старая дата релиза."
                + " Можно добавить фильмы с датой релиза после 28.12.1895";
        assertEquals(exceptionMessage, ex.getMessage());
    }


}