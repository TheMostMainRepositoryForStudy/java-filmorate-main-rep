package ru.yandex.practicum.javafilmorate.model;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder
public class Mpa {

    private int id;

    private String name;

    public Mpa(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Mpa(int id) {
        this.id = id;
    }

    public Mpa() {
    }
}
