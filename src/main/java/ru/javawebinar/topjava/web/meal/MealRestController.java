package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.ValidationUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.slf4j.LoggerFactory.getLogger;
import static ru.javawebinar.topjava.web.SecurityUtil.authUserId;

@Controller
public class MealRestController {
    private static final Logger log = getLogger(MealRestController.class);

    @Autowired
    private MealService service;

    public void create(Meal meal) {
        ValidationUtil.checkNew(meal);
        log.info("create new meal userId: {}", authUserId());
        service.create(meal, authUserId());
    }

    public void delete(int mealId) {
        log.info("delete meal {} userId: {}", mealId, authUserId());
        service.delete(mealId, authUserId());
    }

    public Meal get(int mealId) {
        log.info("get meal {}, userId {}", mealId, authUserId());
        return service.get(mealId, authUserId());
    }

    public void update(Meal meal, int mealId) {
        ValidationUtil.assureIdConsistent(meal, mealId);
        log.info("update meal {}, userId: {}", mealId, authUserId());
        service.update(meal, authUserId());
    }

    public List<MealTo> getAll() {
        log.info("get all for user {}", authUserId());
        return MealsUtil.getTos(service.getAll(authUserId()), MealsUtil.DEFAULT_CALORIES_PER_DAY);
    }

    public List<MealTo> getAllFiltered(LocalDate fromDate, LocalDate toDate, LocalTime fromTime, LocalTime toTime) {
        log.info("get all filtered for user {}", authUserId());
        List<Meal> meals = new ArrayList<>(service.getAll(authUserId()));
        return MealsUtil.getFilteredTos(meals, MealsUtil.DEFAULT_CALORIES_PER_DAY, fromTime, toTime).stream()
                .filter(mealTo -> mealTo.getDateTime().toLocalDate().compareTo(fromDate) >= 0
                        && mealTo.getDateTime().toLocalDate().compareTo(toDate) <= 0)
                .collect(Collectors.toList());
    }
}