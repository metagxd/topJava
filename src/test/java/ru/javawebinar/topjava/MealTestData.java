package ru.javawebinar.topjava;

import org.assertj.core.api.Assertions;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;

import static org.assertj.core.api.Assertions.assertThat;

public class MealTestData {

    public static final Meal UPDATED_MEAL = new Meal(100003, LocalDateTime.of(2021, Month.FEBRUARY, 1, 15, 0), "updated meal", 505);
    public static final Meal NEW_MEAL = new Meal(LocalDateTime.of(2020, Month.FEBRUARY, 1, 15, 0), "new meal", 1000);
    public static final Meal SAVED_NEW_MEAL = new Meal(100_008, LocalDateTime.of(2020, Month.FEBRUARY, 1, 15, 0), "new meal", 1000);
    public static final Meal BREAKFAST_MEAL_USER = new Meal(100_002, LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500);
    public static final Meal LUNCH_MEAL_USER = new Meal(100_003, LocalDateTime.of(2020, Month.JANUARY, 30, 13, 15), "Обед", 1000);
    public static final Meal DINNER_MEAL_USER = new Meal(100_004, LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500);
    public static final Meal BREAKFAST_MEAL_USER_NEXT_DAY = new Meal(100_005, LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 500);
    public static final Meal LUNCH_MEAL_ADMIN = new Meal(100_006, LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 550);
    public static final Meal DINNER_MEAL_ADMIN = new Meal(100_007, LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 500);

    public static void assertMatch(Meal expected, Meal actual) {
        Assertions.assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).usingFieldByFieldElementComparator().isEqualTo(expected);
    }

    public static Meal makeNew(Meal meal) {
        meal.setId(null);
        return meal;
    }
}
