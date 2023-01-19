package ru.yandex.practicum.javafilmorate.util;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.javafilmorate.model.Film;
import ru.yandex.practicum.javafilmorate.model.User;

import java.time.Duration;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class StringValidatorTest {

    private final static int MAX_DESCRIPTION_LENGTH = 200;

    @Test
    void isLengthBiggerThanMaxLength() {

        String tooLongDescription = "Test description Test description Test description" +
                " Test description Test description Test description" +
                "Test description Test description Test description" +
                "Test description Test description Test description" +
                "Test description Test description Test description";

        Film titanicWithTooLongDescription = Film.builder().id(1)
                .name("Titanic")
                .description(tooLongDescription)
                .duration(Duration.ofMinutes(90))
                .releaseDate(LocalDate.of(1997, 1, 23))
                .build();
        assertTrue(StringValidator.isLengthBiggerThanMaxLength(titanicWithTooLongDescription.getDescription(),
                MAX_DESCRIPTION_LENGTH));
    }

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

    @Test
    void isEmptyOrContainsSpaceSymbol() {

        User userWithEmptyLogin = User.builder()
                .id(1)
                .email("Test@mail.com")
                .login("")
                .name("TestName")
                .birthday(LocalDate.of(1993, 2, 28))
                .build();

        User userWithBlankInLogin = User.builder()
                .id(1)
                .email("Test@mail.com")
                .login("Test Login")
                .name("TestName")
                .birthday(LocalDate.of(1993, 2, 28))
                .build();

        assertTrue(StringValidator.isEmptyOrContainsSpaceSymbol(userWithEmptyLogin.getLogin()));
        assertTrue(StringValidator.isEmptyOrContainsSpaceSymbol(userWithBlankInLogin.getLogin()));
    }
}