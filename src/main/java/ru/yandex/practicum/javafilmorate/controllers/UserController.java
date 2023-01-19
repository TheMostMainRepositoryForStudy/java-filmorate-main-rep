package ru.yandex.practicum.javafilmorate.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.javafilmorate.exceptions.*;
import ru.yandex.practicum.javafilmorate.model.User;
import ru.yandex.practicum.javafilmorate.util.LocalDateValidator;
import ru.yandex.practicum.javafilmorate.util.StringValidator;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {

    private int idToNewUser = 1;

    private int generateUserId() {
        int idToSet = idToNewUser;
        idToNewUser++;
        return idToSet;
    }

    private final HashMap<Integer, User> users = new HashMap<>();

    @PostMapping
    public User addNewUser(@RequestBody @Valid User user) {

        log.info("Получен запрос 'POST /users'");

        if (validateUser(user)) {
            // Проверяю, что в коллекции нет пользователья с такими же именем, логином, email
            // и датой рождения
            if (users.containsValue(user)) {
                String exceptionMessage = "Пользователь уже добавлен";
                log.warn("Ошибка при добавлении нового пользователя. Сообщение исключения: {}", exceptionMessage);
                throw new EntityAlreadyExistsException("Пользователь уже добавлен");
            }
            System.out.println(user);
            user.setId(generateUserId());
            users.put(user.getId(), user);
            return user;
        }
        return null;
    }

    @PutMapping
    public User updateUser(@RequestBody @Valid User user) {
        if (validateUser(user)) {

            log.info("Получен запрос 'PUT /users'");
            if (!users.containsKey(user.getId())) {
                String exceptionMessage = "Обновляемый пользователь не существует";
                log.warn("Ошибка при обновлении пользователя. Сообщение исключения: {}", exceptionMessage);
                throw new EntityDoesNotExistException(exceptionMessage);
            }
            users.remove(user.getId(), user);
            users.put(user.getId(), user);
            return user;
        }
        return null;
    }

    @GetMapping
    public List<User> getAll() {

        log.info("Получен запрос 'GET /users'");
        return new ArrayList<>(users.values());
    }

    public boolean validateUser(User user) {

        if (StringValidator.isNullOrEmpty(user.getEmail())) {
            String exceptionMessage = "Email пользователя не может быть"
                    + " не задан или быть пустой строкой";
            log.warn("Ошибка при валидации пользователя. Сообщение исключения: {}", exceptionMessage);
            throw new UserValidationException(exceptionMessage);
        }

        if (!user.getEmail().contains("@")) {
            String exceptionMessage = "Email пользователя должен содержать '@'";
            log.warn("Ошибка при валидации пользователя. Сообщение исключения: {}", exceptionMessage);
            throw new UserValidationException(exceptionMessage);
        }

        if (StringValidator.isEmptyOrContainsSpaceSymbol(user.getLogin())) {
            String exceptionMessage = "Логин пользователя не должен быть пустым" +
                    "и содержать пробелы";
            log.warn("Ошибка при валидации пользователя. Сообщение исключения: {}", exceptionMessage);
            throw new UserValidationException(exceptionMessage);
        }

        if (StringValidator.isNullOrEmpty(user.getName())) {
            user.setName(user.getLogin());
        }

        if (LocalDateValidator.isDateInTheFuture(user.getBirthday())) {
            String exceptionMessage = "Пользователь не может быть рождён в будущем";
            log.warn("Ошибка при валидации пользователя. Сообщение исключения: {}", exceptionMessage);
            throw new UserValidationException(exceptionMessage);
        }
        return true;
    }
}
