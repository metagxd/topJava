package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;

import java.util.Collection;

public interface MealRepository {
    // null if updated meal do not belong to userId
    Meal save(int userId, Meal meal);

    // false if meal do not belong to userId
    boolean delete(int userId, int MealId);

    // null if meal do not belong to userId
    Meal get(int mealId, int userId);

    // ORDERED dateTime desc
    Collection<Meal> getAll(int userId);
}
