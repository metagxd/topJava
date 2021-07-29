package ru.javawebinar.topjava.util;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.lang.Nullable;
import ru.javawebinar.topjava.Profiles;

public class Util {
    private Util() {
    }

    public static <T extends Comparable<T>> boolean isBetweenHalfOpen(T value, @Nullable T start, @Nullable T end) {
        return (start == null || value.compareTo(start) >= 0) && (end == null || value.compareTo(end) < 0);
    }

    public static void ResolveSpringApplicationContext(ConfigurableApplicationContext applicationContext) {
        applicationContext.getEnvironment().setActiveProfiles(Profiles.getActiveDbProfile(), Profiles.REPOSITORY_IMPLEMENTATION);
        ((ClassPathXmlApplicationContext) applicationContext).setConfigLocations("spring/spring-app.xml", "spring/spring-db.xml");
        applicationContext.refresh();
    }
}