package ru.yandex.practicum.javafilmorate.model;

import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.Duration;
import java.time.LocalDate;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder
public class Film {

    @EqualsAndHashCode.Exclude
    private int id;
    @NotNull
    private String name;
    @Size(max = 200, message = "Длина описания не должна превышать 200 символов")
    private String description;
    private LocalDate releaseDate;
    private Duration duration;
}
