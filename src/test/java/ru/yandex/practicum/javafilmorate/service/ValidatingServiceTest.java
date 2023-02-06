package ru.yandex.practicum.javafilmorate.service;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ru.yandex.practicum.javafilmorate.model.Film;
import ru.yandex.practicum.javafilmorate.model.User;

import javax.validation.ConstraintViolationException;

import java.time.Duration;
import java.time.LocalDate;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@RequiredArgsConstructor
class ValidatingServiceTest {

    @Autowired
    private ValidatingService service;

    @Test
    void whenFilmNameIsEmpty_thenThrowsException() {
        final Film titanicWithEmptyName = Film.builder().id(1)
                .name("")
                .description("Test description")
                .duration(Duration.ofMinutes(90))
                .releaseDate(LocalDate.of(1997, 1, 23))
                .build();

        assertThrows(ConstraintViolationException.class, () -> {
            service.validateInputWithInjectedValidator(titanicWithEmptyName);
        });
    }

    @Test
    void whenFilmDescriptionIsTooLong_thenThrowsException() {

        final Film titanicWithTooLongDescription = Film.builder().id(1)
                .name("Titanic")
                .description(generateLetterString(201))
                .duration(Duration.ofMinutes(90))
                .releaseDate(LocalDate.of(1997, 1, 23))
                .build();

        assertThrows(ConstraintViolationException.class, () -> {
            service.validateInputWithInjectedValidator(titanicWithTooLongDescription);
        });
    }

    @Test
    void whenFilmDurationIsNegative_thenThrowsException() {

        final Film titanicWithNegativeDuration = Film.builder().id(1)
                .name("Titanic")
                .description("Test Description")
                .duration(Duration.ofMinutes(-90))
                .releaseDate(LocalDate.of(1997, 1, 23))
                .build();

        assertThrows(ConstraintViolationException.class, () -> {
            service.validateInputWithInjectedValidator(titanicWithNegativeDuration);
        });
    }

    @Test
    void whenFilmReleaseDateIsTooEarly_thenThrowsException() {

        final Film titanicWithTooOldReleaseDate = Film.builder().id(1)
                .name("Titanic")
                .description("Test Description")
                .duration(Duration.ofMinutes(90))
                .releaseDate(LocalDate.of(1797, 1, 23))
                .build();

        assertThrows(ConstraintViolationException.class, () -> {
            service.validateInputWithInjectedValidator(titanicWithTooOldReleaseDate);
        });
    }

    @Test
    void whenUserWithoutEmail_thenThrowsException() {

        final User userWithoutEmail = User.builder()
                .id(1)
                .email(null)
                .login("TestLogin")
                .name("TestName")
                .birthday(LocalDate.of(1993, 2, 28))
                .build();

        assertThrows(ConstraintViolationException.class, () -> {
            service.validateInputWithInjectedValidator(userWithoutEmail);
        });
    }

    @Test
    void whenUserWithIncorrectEmail_thenThrowsException() {

        final User userWithIncorrectEmail = User.builder()
                .id(1)
                .email("Test.mail")
                .login("TestLogin")
                .name("TestName")
                .birthday(LocalDate.of(1993, 2, 28))
                .build();

        assertThrows(ConstraintViolationException.class, () -> {
            service.validateInputWithInjectedValidator(userWithIncorrectEmail);
        });
    }

    @Test
    void whenUserWithEmptyLogin_thenThrowsException() {

        final User userWithEmptyLogin = User.builder()
                .id(1)
                .email("Test@mail.com")
                .login("")
                .name("TestName")
                .birthday(LocalDate.of(1993, 2, 28))
                .build();

        assertThrows(ConstraintViolationException.class, () -> {
            service.validateInputWithInjectedValidator(userWithEmptyLogin);
        });
    }

    @Test
    void whenUserWithBlankInLogin_thenThrowsException() {

        final User userWithBlankInLogin = User.builder()
                .id(1)
                .email("Test@mail.com")
                .login("Test Login")
                .name("TestName")
                .birthday(LocalDate.of(1993, 2, 28))
                .build();

        assertThrows(ConstraintViolationException.class, () -> {
            service.validateInputWithInjectedValidator(userWithBlankInLogin);
        });
    }

    @Test
    void whenUserWithBirthdayInTheFuture_thenThrowsException() {

        final User userWithBirthdayInTheFuture = User.builder()
                .id(1)
                .email("Test@mail.com")
                .login("TestLogin")
                .name("TestName")
                .birthday(LocalDate.of(2100, 2, 28))
                .build();

        assertThrows(ConstraintViolationException.class, () -> {
            service.validateInputWithInjectedValidator(userWithBirthdayInTheFuture);
        });
    }

    String generateLetterString(int targetStringLength) {
        int leftLimit = 97;
        int rightLimit = 122;
        Random random = new Random();

        return random.ints(leftLimit, rightLimit + 1)
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }
}