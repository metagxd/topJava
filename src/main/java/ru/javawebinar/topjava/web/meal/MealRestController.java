package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.util.List;
import java.util.function.Predicate;

import static org.slf4j.LoggerFactory.getLogger;

@Controller
public class MealRestController {
    private static final Logger log = getLogger(MealRestController.class);
    private final int userId = SecurityUtil.authUserId();
    @Autowired
    private MealService service;

    public void create(Meal meal) {
        log.info("create new meal userId: {}", userId);
        service.create(meal, userId);
    }

    public void delete(int mealId) {
        log.info("delete meal {} userId: {}", mealId, userId);
        service.delete(mealId, userId);
    }

    public Meal get(int mealId) {
        log.info("get meal {}, userId {}", mealId, userId);
        return service.get(mealId, userId);
    }

    public void update(Meal meal, int mealId) {
        log.info("update meal {}, userId: {}", mealId, userId);
        service.update(meal, userId);
    }

    public List<MealTo> getAll() {
        log.info("get all for user {}", userId);
        return MealsUtil.getTos(service.getAll(userId), MealsUtil.DEFAULT_CALORIES_PER_DAY);
    }

    List<MealTo> getAllFiltered(Predicate<Meal> predicate) {
        log.info("get all filtered for user {}", userId);
        return MealsUtil.filterByPredicate(service.getAll(userId), MealsUtil.DEFAULT_CALORIES_PER_DAY, predicate);
    }
}