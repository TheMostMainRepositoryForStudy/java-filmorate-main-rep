package ru.yandex.practicum.javafilmorate.model;

import lombok.*;
import ru.yandex.practicum.javafilmorate.enums.RatingMPA;
import ru.yandex.practicum.javafilmorate.util.DurationPositiveOrZero;
import ru.yandex.practicum.javafilmorate.util.FilmDate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.Duration;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder
public class Film {

    @EqualsAndHashCode.Exclude
    private long id;
    @NotNull
    @NotBlank
    private String name;
    @Size(max = 200, message = "Длина описания не должна превышать 200 символов")
    private String description;
    @FilmDate
    private LocalDate releaseDate;
    @DurationPositiveOrZero
    private Duration duration;
    private Set<Long> likes;
    private int likesAmount;
    private HashMap<Long, Boolean> friends;
    private RatingMPA rating;
    private List<RatingMPA> listMpa;
    private Mpa mpa;
    private HashMap<String, Integer> hashMpa;

    public Film() {

    }

    public Film(long id, String name, String description, LocalDate releaseDate, Duration duration, Set<Long> likes, int likesAmount, HashMap<Long, Boolean> friends, RatingMPA rating, List<RatingMPA> listMpa, Mpa mpa, HashMap<String, Integer> hashMpa) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.releaseDate = releaseDate;
        this.duration = duration;
        this.likes = likes;
        this.likesAmount = likesAmount;
        this.friends = friends;
        this.rating = rating;
        this.listMpa = listMpa;
        this.mpa = mpa;
        this.hashMpa = hashMpa;
    }
}
