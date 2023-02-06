package ru.yandex.practicum.javafilmorate.util;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({FIELD})
@Retention(RUNTIME)
@Constraint(validatedBy = DurationPositiveOrZeroValidator.class)
@Documented
public @interface DurationPositiveOrZero {

    String message() default "{Длительность фильма должна быть больше или равна 0 сек.}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
