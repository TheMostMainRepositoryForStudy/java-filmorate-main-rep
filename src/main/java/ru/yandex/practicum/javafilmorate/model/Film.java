package ru.yandex.practicum.javafilmorate.model;

import lombok.*;
import ru.yandex.practicum.javafilmorate.util.DurationPositiveOrZero;
import ru.yandex.practicum.javafilmorate.util.FilmDate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder
public class Film implements Serializable {

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
    private List<User> likes;
    private int rate;
    private Mpa mpa;

    private List<Genre> genres;

    public Film() {

    }

    public Film(long id,
                String name,
                String description,
                LocalDate releaseDate,
                Duration duration,
                List<User> likes,
                int rate,
                Mpa mpa,
                List<Genre> genres) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.releaseDate = releaseDate;
        this.duration = duration;
        this.likes = likes;
        this.rate = rate;
        this.mpa = mpa;
        this.genres = genres;
    }

    public Map<String, Object> toMap(){
        Map<String,Object> values = new HashMap<>();
        values.put("name", name);
        values.put("description", description);
        values.put("release_date", releaseDate);
        values.put("duration", duration);
        values.put("mpa", mpa);
        return values;
    }
}
