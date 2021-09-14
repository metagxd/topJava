package ru.javawebinar.topjava.web.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class StringToLocalTime implements Converter<String, LocalTime> {

    @Override
    public LocalTime convert(@NonNull String source) {
        if (source.isEmpty()) {
            return null;
        }
        return LocalTime.parse(source, DateTimeFormatter.ISO_LOCAL_TIME);
    }
}
