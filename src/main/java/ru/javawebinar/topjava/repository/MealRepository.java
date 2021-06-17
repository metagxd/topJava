package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDate;
import java.util.Collection;

public interface MealRepository {
    // null if updated meal do not belong to userId
    Meal save(Meal meal, int userId);

    // false if meal do not belong to userId
    boolean delete(int mealId, int userId);

    // null if meal do not belong to userId
    Meal get(int mealId, int userId);

    // ORDERED dateTime desc
    Collection<Meal> getAll(int userId);

    Collection<Meal> getFilteredByDate(int userId, LocalDate fromDate, LocalDate toDate);
}
