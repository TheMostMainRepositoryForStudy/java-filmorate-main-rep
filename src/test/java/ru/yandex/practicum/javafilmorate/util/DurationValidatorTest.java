package ru.yandex.practicum.javafilmorate.util;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.javafilmorate.model.Film;

import java.time.Duration;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class DurationValidatorTest {

    @Test
    void isDurationNegativeOrZero() {

        Film titanicWithNegativeDuration = Film.builder().id(1)
                .name("Titanic")
                .description("Test Description")
                .duration(Duration.ofMinutes(-90))
                .releaseDate(LocalDate.of(1997, 1, 23))
                .build();

        assertTrue(DurationValidator.isDurationNegativeOrZero(titanicWithNegativeDuration.getDuration()));
    }
}