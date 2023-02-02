package ru.yandex.practicum.javafilmorate.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.javafilmorate.exceptions.*;
import ru.yandex.practicum.javafilmorate.model.User;
import ru.yandex.practicum.javafilmorate.service.UserService;
import ru.yandex.practicum.javafilmorate.util.LocalDateValidator;
import ru.yandex.practicum.javafilmorate.util.StringValidator;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public User addNewUser(@RequestBody @Valid User user) {
        log.info("Получен запрос 'POST /users'");
        validateUser(user);
        return userService.addNewUserToStorage(user);
    }

    @PutMapping
    public User updateUser(@RequestBody @Valid User user) {
        log.info("Получен запрос 'PUT /users'");
        validateUser(user);
        return userService.updateUserInStorage(user);
    }

    @GetMapping
    public List<User> getAll() {
        log.info("Получен запрос 'GET /users'");
        return userService.getAllUsersFromStorage();
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable long id) {
        log.info( String.format("Получен запрос 'GET /users/%d", id));
        checkUserExistence(id);
        return userService.getUserFromStorage(id);
    }

    @PutMapping("/{id}/friends/{friendId}")
    public void addFriend(@PathVariable long id, @PathVariable long friendId) {
        log.info( String.format("Получен запрос 'PUT /users/%d/friends/%d'", id, friendId));
        checkUserExistence(id);
        checkUserExistence(friendId);
        userService.addUsersToFriendsInStorage(id, friendId);
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    public void deleteFriend(@PathVariable long id, @PathVariable long friendId) {
        log.info( String.format("Получен запрос 'DELETE /users/%d/friends/%d'", id, friendId));
        checkUserExistence(id);
        checkUserExistence(friendId);
        userService.deleteUsersFromFriendsInStorage(id, friendId);
    }

    @GetMapping("/{id}/friends")
    public List<User> getUserFriends(@PathVariable long id){
        log.info( String.format("Получен запрос 'GET /users/%d/friends'", id));
        checkUserExistence(id);
        return userService.getUserFriendsFromStorage(id);
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    public List<User> getUsersCommonFriends(@PathVariable long id, @PathVariable long otherId){
        log.info( String.format("Получен запрос 'GET /users/%d/friends/common/%d", id, otherId));
        checkUserExistence(id);
        checkUserExistence(otherId);
        return userService.getUsersCommonFriendsFromStorage(id, otherId);
    }

    private void checkUserExistence(long id){

        if(id < 1){
            String exceptionMessage = String.format("Пользователь с  id = %d не может существовать", id);
            log.warn("Ошибка при запросе пользователя. Сообщение исключения: {}",
                    exceptionMessage);
            throw new InvalidPathVariableOrParameterException("id", exceptionMessage);
        }

        if(!userService.doesUserExistInStorage(id)){
            String exceptionMessage = String.format("Пользователь с  id = %d не существует", id);
            log.warn("Ошибка при запросе пользователя. Сообщение исключения: {}",
                    exceptionMessage);
            throw new EntityDoesNotExistException( exceptionMessage);
        }
    }

    private void validateUser(User user) {

        if (StringValidator.isNullOrEmpty(user.getEmail())) {
            String exceptionMessage = "Email пользователя не может быть" + " не задан или быть пустой строкой";
            log.warn("Ошибка при валидации пользователя. Сообщение исключения: {}", exceptionMessage);
            throw new ValidationException(exceptionMessage);
        }

        if (!user.getEmail().contains("@")) {
            String exceptionMessage = "Email пользователя должен содержать '@'";
            log.warn("Ошибка при валидации пользователя. Сообщение исключения: {}", exceptionMessage);
            throw new ValidationException(exceptionMessage);
        }

        if (StringValidator.isEmptyOrContainsSpaceSymbol(user.getLogin())) {
            String exceptionMessage = "Логин пользователя не должен быть пустым" + "и содержать пробелы";
            log.warn("Ошибка при валидации пользователя. Сообщение исключения: {}", exceptionMessage);
            throw new ValidationException(exceptionMessage);
        }

        if (StringValidator.isNullOrEmpty(user.getName())) {
            user.setName(user.getLogin());
        }

        if (LocalDateValidator.isDateInTheFuture(user.getBirthday())) {
            String exceptionMessage = "Пользователь не может быть рождён в будущем";
            log.warn("Ошибка при валидации пользователя. Сообщение исключения: {}", exceptionMessage);
            throw new ValidationException(exceptionMessage);
        }
    }
}
