package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public interface MealRepository {

    boolean save(Meal meal);

    boolean ubdate(Meal meal);

    boolean delete(int id);

    Meal getById(int id);

    List<Meal> getAll();
}
