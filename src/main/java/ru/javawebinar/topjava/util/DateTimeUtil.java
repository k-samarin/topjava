package ru.javawebinar.topjava.util;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;

public class DateTimeUtil {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static boolean isBetweenHalfOpen(TemporalAccessor testTime, TemporalAccessor startTime, TemporalAccessor endTime) {
        LocalTime lt = LocalTime.from(testTime);
        return lt.compareTo(LocalTime.from(startTime)) >= 0 && lt.compareTo(LocalTime.from(endTime)) < 0;
    }

    public static String toString(LocalDateTime ldt) {
        return ldt == null ? "" : ldt.format(DATE_TIME_FORMATTER);
    }
}

