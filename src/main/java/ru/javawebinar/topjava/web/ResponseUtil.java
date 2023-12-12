package ru.javawebinar.topjava.web;

import org.springframework.validation.FieldError;

import java.util.List;
import java.util.stream.Collectors;

public class ResponseUtil {
    private ResponseUtil(){
    }

    public static String GetErrorResponseBody(List<FieldError> fieldErrors){
            return fieldErrors.stream()
                    .map(fe -> String.format("[%s] %s", fe.getField(), fe.getDefaultMessage()))
                    .collect(Collectors.joining("<br>"));
    }
}
