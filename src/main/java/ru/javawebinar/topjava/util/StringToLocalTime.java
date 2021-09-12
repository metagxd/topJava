package ru.javawebinar.topjava.util;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;

import java.time.LocalTime;

public class StringToLocalTime implements Converter<String, LocalTime> {

    @Override
    public LocalTime convert(@NonNull String source) {
        return LocalTime.parse(source);
    }
}
