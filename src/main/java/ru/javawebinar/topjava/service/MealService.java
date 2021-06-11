package ru.javawebinar.topjava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.ValidationUtil;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.stream.Collectors;

@Service
public class MealService {

    @Autowired
    private MealRepository repository;

    public void create(int userId, Meal meal) {
        repository.save(userId, meal);
    }

    public void update(int userId, Meal meal) {
        if (repository.save(userId, meal) == null) {
            throw new NotFoundException("User ID: " + userId + " Meal ID: " + meal.getId());
        }
    }

    public void delete(int userId, int mealId) {
        ValidationUtil.checkNotFound(repository.delete(userId, mealId), String.valueOf(mealId));
    }

    public Meal get(int userId, int mealId) {
        return ValidationUtil.checkNotFound(repository.get(userId, mealId), "User ID: " + userId + " Meal ID: " + mealId);
    }

    public Collection<Meal> getAll(int userId) {
        Collection<Meal> meals = repository.getAll(userId);
        if (meals == null) {
            return Collections.emptyList();
        } else {
            return meals
                    .stream().parallel()
                    .sorted(Comparator.comparing(Meal::getDateTime, Comparator.reverseOrder()))
                    .collect(Collectors.toList());
        }
    }
}