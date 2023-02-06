package ru.yandex.practicum.javafilmorate.util;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.Duration;
import java.time.LocalDate;

public class FilmDateValidator implements ConstraintValidator<FilmDate, LocalDate> {
    private final static LocalDate THE_OLDEST_RELEASE_DATE = LocalDate.of(1895, 12, 28);

    @Override
    public boolean isValid(LocalDate dateToCheck, ConstraintValidatorContext constraintValidatorContext) {
        return dateToCheck.isAfter(THE_OLDEST_RELEASE_DATE);
    }
}
