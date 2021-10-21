package com.anair.addressbook.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;

public final class DateUtils {
    private DateUtils() {
    }

    public static LocalDate parseDateString(final CharSequence date) {
        var formatter = new DateTimeFormatterBuilder().appendPattern("d/M/")
                .optionalStart()
                .appendPattern("uuuu")
                .optionalEnd()
                .optionalStart()
                .appendValueReduced(ChronoField.YEAR, 2, 2, LocalDate.now().minusYears(80))
                .optionalEnd()
                .toFormatter();

        return LocalDate.parse(date, formatter);
    }
}
