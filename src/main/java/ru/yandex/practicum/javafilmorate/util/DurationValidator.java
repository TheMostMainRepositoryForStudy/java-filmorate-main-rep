package ru.yandex.practicum.javafilmorate.util;

import java.time.Duration;

public final class DurationValidator {

    public static boolean isDurationNegativeOrZero(Duration duration) {

        return duration.toSeconds() <= 0;
    }

}
