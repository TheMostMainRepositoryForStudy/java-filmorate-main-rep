package ru.yandex.practicum.javafilmorate.util;

import java.time.LocalDate;

public final class LocalDateValidator {

    public static boolean isDateTooOld(LocalDate dateToCheck, LocalDate theOldestPossibleDate) {

        return dateToCheck.isBefore(theOldestPossibleDate);
    }

    public static boolean isDateInTheFuture(LocalDate dateToCheck) {

        return dateToCheck.isAfter(LocalDate.now());
    }
}
