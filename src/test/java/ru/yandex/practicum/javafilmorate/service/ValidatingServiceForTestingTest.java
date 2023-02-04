package ru.yandex.practicum.javafilmorate.service;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import ru.yandex.practicum.javafilmorate.model.Film;
import ru.yandex.practicum.javafilmorate.model.User;

import javax.validation.ConstraintViolationException;

import java.time.Duration;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@RequiredArgsConstructor
class ValidatingServiceForTestingTest {


    private static Film titanicWithEmptyName;
    private static Film titanicWithTooLongDescription;
    private static Film titanicWithNegativeDuration;
    private static Film titanicWithTooOldReleaseDate;

    private static User userWithoutEmail;

    private static User userWithIncorrectEmail;

    private static User userWithEmptyLogin;

    private static User userWithBlankInLogin;

    private static User userWithBirthdayInTheFuture;

    @BeforeAll
    static void beforeAll() {

        titanicWithEmptyName = Film.builder().id(1)
                .name("")
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

        userWithoutEmail = User.builder()
                .id(1)
                .email(null)
                .login("TestLogin")
                .name("TestName")
                .birthday(LocalDate.of(1993, 2, 28))
                .build();

        userWithIncorrectEmail = User.builder()
                .id(1)
                .email("Test.mail")
                .login("TestLogin")
                .name("TestName")
                .birthday(LocalDate.of(1993, 2, 28))
                .build();

        userWithEmptyLogin = User.builder()
                .id(1)
                .email("Test@mail.com")
                .login("")
                .name("TestName")
                .birthday(LocalDate.of(1993, 2, 28))
                .build();

        userWithBlankInLogin = User.builder()
                .id(1)
                .email("Test@mail.com")
                .login("Test Login")
                .name("TestName")
                .birthday(LocalDate.of(1993, 2, 28))
                .build();

        userWithBirthdayInTheFuture = User.builder()
                .id(1)
                .email("Test@mail.com")
                .login("TestLogin")
                .name("TestName")
                .birthday(LocalDate.of(2100, 2, 28))
                .build();
    }

    @Autowired
    private ValidatingServiceForTesting service;

    @Test
    void whenFilmNameIsEmpty_thenThrowsException() {

        assertThrows(ConstraintViolationException.class, () -> {
            service.validateInputWithInjectedValidator(titanicWithEmptyName);
        });
    }

    @Test
    void whenFilmDescriptionIsTooLong_thenThrowsException() {

        assertThrows(ConstraintViolationException.class, () -> {
            service.validateInputWithInjectedValidator(titanicWithNegativeDuration);
        });
    }

    @Test
    void whenFilmDurationIsNegative_thenThrowsException() {

        assertThrows(ConstraintViolationException.class, () -> {
            service.validateInputWithInjectedValidator(titanicWithTooLongDescription);
        });
    }

    @Test
    void whenFilmReleaseDateIsTooEarly_thenThrowsException() {

        assertThrows(ConstraintViolationException.class, () -> {
            service.validateInputWithInjectedValidator(titanicWithTooOldReleaseDate);
        });
    }

    @Test
    void whenUserWithoutEmail_thenThrowsException() {

        assertThrows(ConstraintViolationException.class, () -> {
            service.validateInputWithInjectedValidator(userWithoutEmail);
        });
    }

    @Test
    void whenUserWithIncorrectEmail_thenThrowsException() {

        assertThrows(ConstraintViolationException.class, () -> {
            service.validateInputWithInjectedValidator(userWithIncorrectEmail);
        });
    }

    @Test
    void whenUserWithEmptyLogin_thenThrowsException() {

        assertThrows(ConstraintViolationException.class, () -> {
            service.validateInputWithInjectedValidator(userWithEmptyLogin);
        });
    }

    @Test
    void whenUserWithBlankInLogin_thenThrowsException() {

        assertThrows(ConstraintViolationException.class, () -> {
            service.validateInputWithInjectedValidator(userWithBlankInLogin);
        });
    }

    @Test
    void whenUserWithBirthdayInTheFuture_thenThrowsException() {

        assertThrows(ConstraintViolationException.class, () -> {
            service.validateInputWithInjectedValidator(userWithBirthdayInTheFuture);
        });
    }
}