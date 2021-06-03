package ru.javawebinar.topjava.repository;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.slf4j.LoggerFactory.getLogger;

public class InMemoryMealRepository implements MealRepository {
    private int id = 0;
    private static final Logger log = getLogger(InMemoryMealRepository.class);
    Map<Integer, Meal> repo = new HashMap<>();

    public InMemoryMealRepository() {

    }

    @Override
    public synchronized boolean save(Meal meal) {
        try {
            log.debug("saving {} ", meal.getId());
            meal.setId(++id);
            repo.put(meal.getId(), meal);
            return true;
        } catch (Exception exception) {
            log.error("error saving", exception);
            return false;
        }
    }

    @Override
    public boolean update(Meal meal) {
        log.debug("updating {}", meal.getId());
        if (repo.containsKey(meal.getId())) {
            repo.replace(meal.getId(), meal);
            return true;
        }
        return false;
    }


    @Override
    public boolean delete(int id) {
        if (repo.containsKey(id)) {
            log.debug("delete {}", id);
            repo.remove(id);
        } else {
            log.error("not found {}", id);
            return false;
        }
        return true;
    }

    @Override
    public Meal getById(int id) {
        return repo.get(id);
    }

    @Override
    public List<Meal> getAll() {
        log.debug("return all meals");
        return repo.entrySet().stream()
                .distinct()
                .map(Map.Entry::getValue)
                .collect(Collectors.toList());
    }
}
