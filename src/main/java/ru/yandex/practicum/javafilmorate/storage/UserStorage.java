package ru.yandex.practicum.javafilmorate.storage;

import ru.yandex.practicum.javafilmorate.model.User;

import java.util.List;


public interface UserStorage {

    User addUser(User user);

    User getUser(Long id);

    User removeUser(Long id);

    User updateUser(User user);

    List<User> getAllUsers();

}
