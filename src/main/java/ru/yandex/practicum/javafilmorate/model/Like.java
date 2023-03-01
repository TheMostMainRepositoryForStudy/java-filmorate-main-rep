package ru.yandex.practicum.javafilmorate.model;

import lombok.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder
public class Like {

    private long id;
    private long filmId;
    private long userId;

    public Like(long id, long filmId, long userId) {
        this.id = id;
        this.filmId = filmId;
        this.userId = userId;
    }
}
