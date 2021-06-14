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

    public void create(Meal meal, int userId) {
        repository.save(meal, userId);
    }

    public void update(Meal meal, int userId) {
        if (repository.save(meal, userId) == null) {
            throw new NotFoundException("User ID: " + userId + " Meal ID: " + meal.getId());
        }
    }

    public void delete(int mealId, int userId) {
        ValidationUtil.checkNotFound(repository.delete(mealId, userId), String.valueOf(mealId));
    }

    public Meal get(int mealId, int userId) {
        return ValidationUtil.checkNotFound(repository.get(mealId, userId), "User ID: " + userId + " Meal ID: " + mealId);
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