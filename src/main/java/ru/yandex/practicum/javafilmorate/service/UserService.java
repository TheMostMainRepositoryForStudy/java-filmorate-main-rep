package ru.yandex.practicum.javafilmorate.service;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.javafilmorate.dao.FriendShipDao;
import ru.yandex.practicum.javafilmorate.exceptions.EntityDoesNotExistException;
import ru.yandex.practicum.javafilmorate.model.User;
import ru.yandex.practicum.javafilmorate.storage.UserStorage;
import ru.yandex.practicum.javafilmorate.util.StringValidator;

import java.util.List;

@Service
@Slf4j
public class UserService {

    private final UserStorage userStorage;
    private final FriendShipDao friendShipDao;

    public UserService(@Qualifier("userStorageDb")UserStorage userStorage, FriendShipDao friendShipDao) {
        this.userStorage = userStorage;
        this.friendShipDao = friendShipDao;
    }

    public User addNewUserToStorage(User user) {
        setUserNameIfNeeded(user);
        return userStorage.addUser(user);
    }

    public List<User> getAllUsersFromStorage() {
        return userStorage.getAllUsers();
    }

    public User updateUserInStorage(User user) {
        return userStorage.updateUser(user);
    }

    public User getUserFromStorage(long id) {
        return userStorage.getUser(id);
    }

    public void addUsersToFriendsInStorage(long id, long friendId) {
        checkIsUserIdNegative(id);
        checkIsUserIdNegative(friendId);
        friendShipDao.addFriends(id, friendId);
    }

    public void deleteUsersFromFriendsInStorage(long id, long friendId) {
        checkIsUserIdNegative(id);
        checkIsUserIdNegative(friendId);
        friendShipDao.deleteFriends(id, friendId);
    }

    public List<User> getUserFriendsFromStorage(long id) {
        return friendShipDao.getUserFriends(id);
    }

    public List<User> getUsersCommonFriendsFromStorage(long id, long otherId) {
        checkIsUserIdNegative(id);
        checkIsUserIdNegative(otherId);
        return friendShipDao.getCommonFriends(id,otherId);
    }

    private void setUserNameIfNeeded(User user) {

        if (StringValidator.isNullOrEmpty(user.getName())) {
            user.setName(user.getLogin());
        }
    }

    public void checkIsUserIdNegative(long id){
        if(id < 0){
            log.debug("Пользователь с отрицательным id {} не может существовать.", id);
            throw new EntityDoesNotExistException(
                    String.format("Пользователь с отрицательным id %d не может существовать.", id));
        }
    }
}
