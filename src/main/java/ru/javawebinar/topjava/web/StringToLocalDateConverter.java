package ru.javawebinar.topjava.web;

import org.springframework.core.convert.converter.Converter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class StringToLocalDateConverter implements Converter<String, LocalDate> {

    static final String DATE_PATTERN = "yyyy-MM-dd";

    static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern(DATE_PATTERN);

    @Override
    public LocalDate convert(String dateString) {
        return dateString.isEmpty() ? null : LocalDate.parse(dateString, FORMATTER);
    }
}
