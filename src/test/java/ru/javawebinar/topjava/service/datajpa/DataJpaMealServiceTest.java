package ru.javawebinar.topjava.service.datajpa;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.service.AbstractMealServiceTest;

import static ru.javawebinar.topjava.MealTestData.ADMIN_MEAL_ID;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;
import static ru.javawebinar.topjava.UserTestData.admin;

@ActiveProfiles(Profiles.DATAJPA)
public class DataJpaMealServiceTest extends AbstractMealServiceTest {
    @Autowired
    private MealService service;

    @Test
    public void getWithUser() {
        Meal mealWithUser = service.getWithUser(ADMIN_MEAL_ID, ADMIN_ID);
        Assertions.assertThat(mealWithUser.getUser()).usingRecursiveComparison().ignoringFields("meals", "registered").isEqualTo(admin);
    }
}
