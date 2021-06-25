package ru.javawebinar.topjava;

import org.assertj.core.api.Assertions;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class MealTestData {

    public static final Meal UPDATED_MEAL = new Meal(100003, LocalDateTime.of(2021, Month.FEBRUARY, 1, 15, 0), "updated meal", 505);
    public static final Meal NEW_MEAL = new Meal(LocalDateTime.of(2021, Month.FEBRUARY, 1, 15, 0), "new meal", 1000);
    public static final Meal DEFAULT_MEAL = new Meal(100004, LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500);
    public static final List<Meal> meals = Arrays.asList(
            new Meal(LocalDateTime.of(2021, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
            new Meal(LocalDateTime.of(2021, Month.JANUARY, 30, 13, 0), "Обед", 1000),
            new Meal(LocalDateTime.of(2021, Month.JANUARY, 30, 20, 0), "Ужин", 500),
            new Meal(LocalDateTime.of(2021, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
            new Meal(LocalDateTime.of(2021, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
            new Meal(LocalDateTime.of(2021, Month.JANUARY, 31, 13, 0), "Обед", 500),
            new Meal(LocalDateTime.of(2021, Month.JANUARY, 31, 20, 0), "Ужин", 410)
    );

    public static void assertMatch(Meal expected, Meal actual) {
        Assertions.assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).usingFieldByFieldElementComparator().isEqualTo(expected);
    }

}
