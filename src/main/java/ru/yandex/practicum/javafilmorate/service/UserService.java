package ru.yandex.practicum.javafilmorate.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.javafilmorate.exceptions.EntityAlreadyExistsException;
import ru.yandex.practicum.javafilmorate.exceptions.EntityDoesNotExistException;
import ru.yandex.practicum.javafilmorate.model.User;
import ru.yandex.practicum.javafilmorate.storage.UserStorage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserStorage userStorage;

    public User addNewUserToStorage(User user) {
        return userStorage.addUser(user);
    }

    public List<User> getAllUsersFromStorage() {
        return userStorage.getAllUsers();
    }

    public User updateUserInStorage(User user) {
        return userStorage.updateUser(user);
    }

    public boolean doesUserExistInStorage(long id) {
        return userStorage.doesUserExist(id);
    }

    public User getUserFromStorage(long id) {
        return userStorage.getUser(id);
    }

    public void addUsersToFriendsInStorage(long id, long friendId) {
        User user = userStorage.getUser(id);
        if (user.getFriends() == null) {
            user.setFriends(new HashSet<>());
        }

        if (!user.getFriends().add(friendId)) {
            throw new EntityAlreadyExistsException("Пользователи уже в друзьях друг у друга");
        }

        User friend = userStorage.getUser(friendId);

        if (friend.getFriends() == null) {
            friend.setFriends(new HashSet<>());
        }

        friend.getFriends().add(id);
    }

    public void deleteUsersFromFriendsInStorage(long id, long friendId) {
        User user = userStorage.getUser(id);
        if (user.getFriends() == null) {
            user.setFriends(new HashSet<>());
        }

        if (!user.getFriends().remove(friendId)) {
            throw new EntityDoesNotExistException("Пользователи не в друзьях друг у друга. Удаление невозможно");
        }

        User friend = userStorage.getUser(friendId);

        if (friend.getFriends() == null) {
            friend.setFriends(new HashSet<>());
        }

        friend.getFriends().remove(id);
    }

    public List<User> getUserFriendsFromStorage(long id) {

        Set<Long> userFriends = userStorage.getUser(id).getFriends();

        if (userFriends == null) {
            return new ArrayList<>();
        }

        return userFriends.stream()
                .map(userStorage::getUser)
                .collect(Collectors.toList());
    }

    public List<User> getUsersCommonFriendsFromStorage(long id, long otherId) {

        Set<Long> userFriends = userStorage.getUser(id).getFriends();

        if (userFriends == null) {
            return new ArrayList<>();
        }

        Set<Long> friendsFriends = userStorage.getUser(otherId).getFriends();

        return userFriends
                .stream()
                .filter(friendsFriends::contains)
                .map(userStorage::getUser)
                .collect(Collectors.toList());
    }
}
