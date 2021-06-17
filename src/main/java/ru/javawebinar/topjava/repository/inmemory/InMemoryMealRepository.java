package ru.javawebinar.topjava.repository.inmemory;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepository implements MealRepository {
    private final Map<Integer, Map<Integer, Meal>> repository = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.meals.forEach(meal -> this.save(meal, SecurityUtil.authUserId()));
    }

    @Override
    public Meal save(Meal meal, int userId) {
        Map<Integer, Meal> userMealMap = repository.getOrDefault(userId, new ConcurrentHashMap<>());
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            userMealMap.put(meal.getId(), meal);
            repository.put(userId, userMealMap);
            return meal;
        } else {
            return userMealMap.computeIfPresent(meal.getId(), (k, v) -> meal);
        }
    }

    @Override
    public boolean delete(int mealId, int userId) {
        Map<Integer, Meal> userMealMap = repository.get(userId);
        if (userMealMap != null) {
            return userMealMap.remove(mealId) != null;
        }
        return false;
    }

    @Override
    public Meal get(int mealId, int userId) {
        Map<Integer, Meal> userMealMap = repository.get(userId);
        if (userMealMap != null) {
            return userMealMap.get(mealId);
        }
        return null;
    }

    @Override
    public Collection<Meal> getAll(int userId) {
        Map<Integer, Meal> userMealMap = repository.get(userId);
        if (userMealMap != null) {
            return userMealMap.values().stream()
                    .sorted(Comparator.comparing(Meal::getDateTime, Comparator.reverseOrder()))
                    .collect(Collectors.toList());
        } else {
            return Collections.emptyList();
        }
    }

    @Override
    public Collection<Meal> getFilteredByDate(int userId, LocalDate fromDate, LocalDate toDate) {
        return getAll(userId).stream()
                .filter(meal -> meal.getDateTime().toLocalDate().compareTo(fromDate) >= 0
                        && meal.getDateTime().toLocalDate().compareTo(toDate) <= 0)
                .collect(Collectors.toList());
    }
}

