package ru.javawebinar.topjava.service.datajpa;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.UserTestData;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.AbstractMealServiceTest;
import ru.javawebinar.topjava.service.MealService;

import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;
import static ru.javawebinar.topjava.UserTestData.admin;

@ActiveProfiles(Profiles.DATAJPA)
public class DataJpaMealServiceTest extends AbstractMealServiceTest {
    @Autowired
    private MealService service;

    @Test
    public void getWithUser() {
        Meal mealWithUser = service.getWithUser(ADMIN_MEAL_ID, ADMIN_ID);
        MATCHER.assertMatch(mealWithUser, adminMeal1);
        UserTestData.MATCHER.assertMatch(mealWithUser.getUser(), admin);
    }
}
