package ru.javawebinar.topjava.web;

import org.springframework.core.convert.converter.Converter;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class StringToLocalTimeConverter implements Converter<String, LocalTime> {
    static final String TIME_PATTERN = "HH:mm";

    static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern(TIME_PATTERN);

    @Override
    public LocalTime convert(String dateString) {
        return dateString.isEmpty() ? null : LocalTime.parse(dateString, FORMATTER);
    }
}
