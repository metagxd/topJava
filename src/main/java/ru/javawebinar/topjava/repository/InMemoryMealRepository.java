package ru.javawebinar.topjava.repository;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static org.slf4j.LoggerFactory.getLogger;

public class InMemoryMealRepository implements MealRepository {
    private static final Logger log = getLogger(InMemoryMealRepository.class);
    Map<Integer, Meal> repo = new ConcurrentHashMap<>();
    private int id = 0;

    public InMemoryMealRepository() {

    }

    @Override
    public Meal save(Meal meal) {
        meal.setId(++id);
        log.debug("saving {} ", meal.getId());
        repo.put(meal.getId(), meal);
        return meal;

    }

    @Override
    public Meal update(Meal meal) {
        log.debug("updating {}", meal.getId());
        return repo.replace(meal.getId(), meal);
    }


    @Override
    public boolean delete(int id) {
        return repo.remove(id) != null;
    }

    @Override
    public Meal getById(int id) {
        return repo.get(id);
    }

    @Override
    public List<Meal> getAll() {
        log.debug("return all meals");
        return new ArrayList<>(repo.values());
    }
}
