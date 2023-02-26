package ru.yandex.practicum.javafilmorate.enums;

public enum RatingMPA {

    G(1),
    PG(2),
    PG_13( 3),
    R(4),
    NC_17(5);

    private final int id;
    RatingMPA(int id) {
        this.id = id;
    }
}
