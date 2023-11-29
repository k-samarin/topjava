package ru.javawebinar.topjava.util;

import org.springframework.core.convert.converter.Converter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class StringToLocalDateConverter implements Converter<String, LocalDate> {

    static final String DATE_PATTERN = "yyyy-MM-dd";

    @Override
    public LocalDate convert(String dateString) {
        return dateString.isEmpty() ? null :
                LocalDate.parse(dateString, DateTimeFormatter.ofPattern(DATE_PATTERN));
    }
}
