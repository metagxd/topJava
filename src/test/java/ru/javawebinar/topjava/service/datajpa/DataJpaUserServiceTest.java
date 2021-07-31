package ru.javawebinar.topjava.service.datajpa;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.UserTestData;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.service.AbstractUserServiceTest;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.service.UserService;

import java.util.Arrays;
import java.util.List;

import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;
import static ru.javawebinar.topjava.UserTestData.admin;

@ActiveProfiles(Profiles.DATAJPA)
public class DataJpaUserServiceTest extends AbstractUserServiceTest {

    @Autowired
    UserService userService;

    @Autowired
    MealService mealService;

    @Test
    public void getWithMeal() {
        User user = userService.getWithMeal(ADMIN_ID);
        List<Meal> adminMealList = Arrays.asList(adminMeal2, adminMeal1);
        UserTestData.MATCHER.assertMatch(user, admin);
        MATCHER.assertMatch(user.getMeals(), adminMealList);
    }

    @Test
    public void getWithMealForUserWithoutMeal() {
        mealService.delete(ADMIN_MEAL_ID, ADMIN_ID);
        mealService.delete(ADMIN_MEAL_ID + 1, ADMIN_ID);
        User user = userService.getWithMeal(ADMIN_ID);
        Assert.assertEquals(user.getMeals().size(), 0);
    }
}
