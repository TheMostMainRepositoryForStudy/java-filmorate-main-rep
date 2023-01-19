package ru.yandex.practicum.javafilmorate.util;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.javafilmorate.model.Film;
import ru.yandex.practicum.javafilmorate.model.User;

import java.time.Duration;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class LocalDateValidatorTest {

    @Test
    void isDateTooOld() {

        Film titanicWithTooOldReleaseDate = Film.builder().id(1)
                .name("Titanic")
                .description("Test Description")
                .duration(Duration.ofMinutes(90))
                .releaseDate(LocalDate.of(1797, 1, 23))
                .build();

        LocalDate THE_OLDEST_RELEASE_DATE = LocalDate.of(1895, 12, 28);

        assertTrue(LocalDateValidator.isDateTooOld(titanicWithTooOldReleaseDate.getReleaseDate(),
                                                                            THE_OLDEST_RELEASE_DATE));
    }

    @Test
    void isDateInTheFuture() {

        User userWithBirthdayInTheFuture = User.builder()
                .id(1)
                .email("Test@mail.com")
                .login("TestLogin")
                .name("TestName")
                .birthday(LocalDate.of(2100, 2, 28))
                .build();

        assertTrue(LocalDateValidator.isDateInTheFuture(userWithBirthdayInTheFuture.getBirthday()));
    }
}