package ru.yandex.practicum.javafilmorate.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.javafilmorate.exceptions.*;
import ru.yandex.practicum.javafilmorate.model.User;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    private int idToNewUser = 1;

    private int generateUserId(){
        int idToSet = idToNewUser;
        idToNewUser++;
        return idToSet;
    }

    HashMap<Integer, User> users = new HashMap<>();

    @PostMapping()
    public User addNewUser(@RequestBody @Valid User user){

        log.info("Получен запрос 'POST /users'");

        if(validateUser(user)){
            // Проверяю, что в коллекции нет пользователья с такими же именем, логином, email
            // и датой рождения
            if(users.containsValue(user)) {
                String exceptionMessage = "Пользователь уже добавлен";
                log.warn("Ошибка при добавлении нового пользователя. Сообщение исключения: {}", exceptionMessage);
                throw new UserAlreadyExistsException("Пользователь уже добавлен");
            }
            user.setId(generateUserId());
            users.put(user.getId(), user);
            return user;
        }
        return null;
    }

    @PutMapping()
    public User updateUser(@RequestBody User user){
        if(validateUser(user)){

            log.info("Получен запрос 'PUT /users'");
            if(!users.containsKey(user.getId())) {
                String exceptionMessage = "Обновляемый пользователь не существует";
                log.warn("Ошибка при обновлении пользователя. Сообщение исключения: {}", exceptionMessage);
                throw new UserDoesNotExistException(exceptionMessage);
            }

            users.remove(user.getId(),user);
            users.put(user.getId(),user);
            return user;
        }
        return null;
    }

    @GetMapping
    public List<User> getAll(){

        log.info("Получен запрос 'GET /users'");
        return new ArrayList<>(users.values());
    }



    public boolean validateUser(User user){

        if(user.getEmail() == null || user.getEmail().isEmpty()){
            String exceptionMessage = "Email пользователя не может быть"
                    + " не задан или быть пустой строкой";
            log.warn("Ошибка при валидации пользователя. Сообщение исключения: {}", exceptionMessage);
            throw new UserEmailNullOrEmptyException(exceptionMessage);
        }

        if(!user.getEmail().contains("@")){
            String exceptionMessage = "Email пользователя должен содержать '@'";
            log.warn("Ошибка при валидации пользователя. Сообщение исключения: {}", exceptionMessage);
            throw new UserEmailIsIncorrectException(exceptionMessage);
        }

        if(user.getLogin().isEmpty() || user.getLogin().contains(" ")){
            String exceptionMessage = "Логин пользователя не должен быть пустым" +
                    "и содержать пробелы";
            log.warn("Ошибка при валидации пользователя. Сообщение исключения: {}", exceptionMessage);
            throw new UserLoginIsEmptyOrBlankException("Логин пользователя не должен быть пустым" +
                    "и содержать пробелы");
        }

        if(user.getName() == null || user.getName().isEmpty()){
            user.setName(user.getLogin());
        }

        if(user.getBirthday().isAfter(LocalDate.now())){
            String exceptionMessage = "Пользователь не может быть рождён в будущем";
            log.warn("Ошибка при валидации пользователя. Сообщение исключения: {}", exceptionMessage);
            throw new UserBirthdayException(exceptionMessage);
        }
        return true;
    }
}
