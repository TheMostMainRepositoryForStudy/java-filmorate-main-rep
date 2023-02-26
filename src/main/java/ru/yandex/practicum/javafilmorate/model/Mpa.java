package ru.yandex.practicum.javafilmorate.model;

import lombok.*;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder
public class Mpa {

    private int id;

    public Mpa(int id) {
        this.id = id;
    }
}
