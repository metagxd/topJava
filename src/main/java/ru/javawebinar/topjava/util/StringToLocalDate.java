package ru.javawebinar.topjava.util;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;

import java.time.LocalDate;

public class StringToLocalDate implements Converter<String, LocalDate> {

    @Override
    public LocalDate convert(@NonNull String source) {
        return LocalDate.parse(source);
    }
}
