package ru.javawebinar.topjava;

import ru.javawebinar.topjava.util.TimeUtil;

import java.time.LocalDateTime;
import java.time.Month;

/**
 * @see <a href="http://topjava.herokuapp.com">Demo application</a>
 * @see <a href="https://github.com/JavaOPs/topjava">Initial project</a>
 */
public class Main {
    public static void main(String[] args) {
        //System.out.format("Hello TopJava Enterprise!");
        LocalDateTime localDateTime = LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0);
        System.out.println(TimeUtil.dateTimeFormatter(localDateTime));
    }
}
