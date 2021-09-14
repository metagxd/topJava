package ru.javawebinar.topjava.web.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class StringToLocalDate implements Converter<String, LocalDate> {

    @Override
    public LocalDate convert(@NonNull String source) {
        if (source.isEmpty()) {
            return null;
        }
        return LocalDate.parse(source, DateTimeFormatter.ISO_LOCAL_DATE);
    }
}
