package ru.yandex.practicum.javafilmorate.controllers;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.javafilmorate.exceptions.UserBirthdayException;
import ru.yandex.practicum.javafilmorate.exceptions.UserEmailIsIncorrectException;
import ru.yandex.practicum.javafilmorate.exceptions.UserEmailNullOrEmptyException;
import ru.yandex.practicum.javafilmorate.exceptions.UserLoginIsEmptyOrBlankException;
import ru.yandex.practicum.javafilmorate.model.User;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserControllerTest {

    public static UserController userController;

    private static User userWithoutEmail;

    private static User userWithIncorrectEmail;

    private static User userWithEmptyLogin;

    private static User userWithBlankInLogin;

    private static User userWithBirthdayInTheFuture;

    @BeforeAll
    static void beforeAll(){

        userController = new UserController();

        userWithoutEmail = User.builder()
                .id(1)
                .login("TestLogin")
                .name("TestName")
                .birthday(LocalDate.of(1993,2,28))
                .build();

        userWithIncorrectEmail = User.builder()
                .id(1)
                .email("Test.mail")
                .login("TestLogin")
                .name("TestName")
                .birthday(LocalDate.of(1993,2,28))
                .build();

        userWithEmptyLogin = User.builder()
                .id(1)
                .email("Test@mail.com")
                .login("")
                .name("TestName")
                .birthday(LocalDate.of(1993,2,28))
                .build();

        userWithBlankInLogin = User.builder()
                .id(1)
                .email("Test@mail.com")
                .login("Test Login")
                .name("TestName")
                .birthday(LocalDate.of(1993,2,28))
                .build();

        userWithBirthdayInTheFuture = User.builder()
                .id(1)
                .email("Test@mail.com")
                .login("TestLogin")
                .name("TestName")
                .birthday(LocalDate.of(2100,2,28))
                .build();
    }

    @Test
    public void shouldReturnUserEmailNullOrEmptyException(){

        UserEmailNullOrEmptyException ex =
                assertThrows(UserEmailNullOrEmptyException.class,
                        () -> userController.validateUser(userWithoutEmail));

        String exceptionMessage = "Email пользователя не может быть"
                + " не задан или быть пустой строкой";
        assertEquals(exceptionMessage, ex.getMessage());
    }

    @Test
    public void shouldReturnUserEmailIsIncorrectException(){

        UserEmailIsIncorrectException ex =
                assertThrows(UserEmailIsIncorrectException.class,
                        () -> userController.validateUser(userWithIncorrectEmail));

        String exceptionMessage = "Email пользователя должен содержать '@'";
        assertEquals(exceptionMessage, ex.getMessage());
    }

    @Test
    public void shouldReturnUserLoginIsEmptyOrBlankException(){

        UserLoginIsEmptyOrBlankException ex =
                assertThrows(UserLoginIsEmptyOrBlankException.class,
                        () -> userController.validateUser(userWithEmptyLogin));

        String exceptionMessage = "Логин пользователя не должен быть пустым" +
                                  "и содержать пробелы";
        assertEquals(exceptionMessage, ex.getMessage());
    }

    @Test
    public void shouldReturnUserLoginIsEmptyOrBlankExceptionBecauseOfBlank(){

        UserLoginIsEmptyOrBlankException ex =
                assertThrows(UserLoginIsEmptyOrBlankException.class,
                        () -> userController.validateUser(userWithBlankInLogin));

        String exceptionMessage = "Логин пользователя не должен быть пустым" +
                "и содержать пробелы";
        assertEquals(exceptionMessage, ex.getMessage());
    }


    @Test
    public void shouldReturnUserBirthdayException(){

        UserBirthdayException ex =
                assertThrows(UserBirthdayException.class,
                        () -> userController.validateUser(userWithBirthdayInTheFuture));

        String exceptionMessage = "Пользователь не может быть рождён в будущем";
        assertEquals(exceptionMessage, ex.getMessage());
    }

}