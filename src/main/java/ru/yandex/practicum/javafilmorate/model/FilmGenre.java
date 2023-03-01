package ru.yandex.practicum.javafilmorate.model;

import lombok.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder
public class FilmGenre {
    private long id;
    private long genreId;
    private long filmId;

    public FilmGenre(long id, long genreId, long filmId) {
        this.id = id;
        this.genreId = genreId;
        this.filmId = filmId;
    }
}
