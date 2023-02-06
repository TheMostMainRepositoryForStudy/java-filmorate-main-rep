package ru.yandex.practicum.javafilmorate.util;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.javafilmorate.model.Film;

import java.time.Duration;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class StringValidatorTest {

    @Test
    void isNullOrEmpty() {
        Film titanicWithEmptyName = Film.builder().id(1)
                .name("")
                .description("Test description")
                .duration(Duration.ofMinutes(90))
                .releaseDate(LocalDate.of(1997, 1, 23))
                .build();

        Film titanicWithNullName = Film.builder().id(1)
                .name("")
                .description("Test description")
                .duration(Duration.ofMinutes(90))
                .releaseDate(LocalDate.of(1997, 1, 23))
                .build();

        assertTrue(StringValidator.isNullOrEmpty(titanicWithEmptyName.getName()));
        assertTrue(StringValidator.isNullOrEmpty(titanicWithNullName.getName()));
    }
}