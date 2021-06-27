package ru.javawebinar.topjava.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {

    static {
        SLF4JBridgeHandler.install();
    }

    @Autowired
    private MealService mealService;

    @Test
    public void get() {
        assertMatch(DINNER_MEAL_USER, mealService.get(100004, USER_ID));
    }

    @Test
    public void getNotFound() {
        Assert.assertThrows(NotFoundException.class, () -> mealService.get(100015, USER_ID));
    }

    @Test
    public void delete() {
        mealService.delete(DINNER_MEAL_USER.getId(), USER_ID);
        Assert.assertThrows(NotFoundException.class, () -> mealService.get(DINNER_MEAL_USER.getId(), USER_ID));
    }

    @Test
    public void getBetweenInclusive() {
        List<Meal> expected = new ArrayList<>(Arrays.asList(
                DINNER_MEAL_USER,
                LUNCH_MEAL_USER,
                BREAKFAST_MEAL_USER
        ));
        List<Meal> actual = mealService.getBetweenInclusive(LocalDate.of(2020, Month.JANUARY, 30), LocalDate.of(2020, Month.JANUARY, 30), USER_ID);
        MealTestData.assertMatch(actual, expected);
    }

    @Test
    public void getAll() {
        List<Meal> expected = new ArrayList<>(Arrays.asList(
                BREAKFAST_MEAL_USER_NEXT_DAY,
                DINNER_MEAL_USER,
                LUNCH_MEAL_USER,
                BREAKFAST_MEAL_USER
        ));
        List<Meal> actual = mealService.getAll(USER_ID);
        MealTestData.assertMatch(actual, expected);
    }

    @Test
    public void update() {
        mealService.update(UPDATED_MEAL, USER_ID);
        assertMatch(UPDATED_MEAL, mealService.get(UPDATED_MEAL.getId(), USER_ID));
    }

    @Test
    public void updateNotFound() {
        Assert.assertThrows(NotFoundException.class, () -> mealService.update(UPDATED_MEAL, ADMIN_ID));
    }

    @Test
    public void create() {
        Meal actual = mealService.create(NEW_MEAL, USER_ID);
        assertMatch(actual, SAVED_NEW_MEAL);
    }

    @Test
    public void createDuplicate() {
        Meal meal = mealService.get(100004, USER_ID);
        makeNew(meal);
        Assert.assertThrows(DataAccessException.class, () -> mealService.create(meal, USER_ID));
    }
}