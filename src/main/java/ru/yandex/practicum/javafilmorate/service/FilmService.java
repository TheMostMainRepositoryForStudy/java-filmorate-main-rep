package ru.yandex.practicum.javafilmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.javafilmorate.exceptions.EntityAlreadyExistsException;
import ru.yandex.practicum.javafilmorate.exceptions.EntityDoesNotExistException;
import ru.yandex.practicum.javafilmorate.model.Film;
import ru.yandex.practicum.javafilmorate.storage.FilmStorage;
import ru.yandex.practicum.javafilmorate.storage.UserStorage;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FilmService {

    private final FilmStorage filmStorage;

    private final UserStorage userStorage;

    public Film addNewFilmToStorage(Film film) {
        return filmStorage.addFilm(film);
    }

    public Film updateFilmInStorage(Film film) {
        return filmStorage.updateFilm(film);
    }

    public List<Film> getAllFilmsFromStorage() {
        return filmStorage.getAllFilms();
    }

    public boolean doesFilmExistInStorage(long id) {
        return filmStorage.doesFilmExist(id);
    }

    public Film getFilmFromStorage(long id) {
        return filmStorage.getFilm(id);
    }

    public void likeFilmInStorage(long id, long userId) {
        Film film = filmStorage.getFilm(id);
        if (film.getLikes() == null) {
            film.setLikes(new HashSet<>());
        }

        if (userStorage.getUser(id) == null) {
            throw new EntityDoesNotExistException(
                    String.format("Пользователь с id = %d не существует", userId));
        }

        if (film.getLikes().contains(userId)) {
            throw new EntityAlreadyExistsException(
                    String.format("Пользователь с userId = %d " +
                            "уже поставил лайк фильму с filmId = %d существует", id, userId));
        }

        film.getLikes().add(userId);
        film.setLikesAmount(film.getLikes().size());
    }

    public void deleteLikeFilmInStorage(long id, long userId) {
        Film film = filmStorage.getFilm(id);
        if (film.getLikes() == null) {
            film.setLikes(new HashSet<>());
        }

        if (userStorage.getUser(id) == null) {
            throw new EntityDoesNotExistException(
                    String.format("Пользователь с id = %d не существует", userId));
        }

        if (!film.getLikes().contains(userId)) {
            throw new EntityDoesNotExistException(
                    String.format("Пользователь с userId = %d " +
                            "не ставил лайк фильму с filmId = %d существует", id, userId));
        }

        film.getLikes().remove(userId);
        film.setLikesAmount(film.getLikes().size());
    }

    public List<Film> getMostLikedFilmsFromStorage(long count) {
        if (filmStorage.getAllFilms().isEmpty()) {
            return filmStorage.getAllFilms();
        }

        return filmStorage.getAllFilms()
                .stream()
                .sorted((o1, o2) -> o2.getLikesAmount() - o1.getLikesAmount())
                .limit(count)
                .collect(Collectors.toList());
    }
}
