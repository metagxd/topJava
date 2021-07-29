package ru.javawebinar.topjava.service.user;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.datajpa.DataJpaUserRepository;

import java.util.Arrays;
import java.util.List;

import static ru.javawebinar.topjava.MealTestData.adminMeal1;
import static ru.javawebinar.topjava.MealTestData.adminMeal2;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;

@ActiveProfiles("datajpa")
public class DataJpaUserServiceTest extends AbstractUserServiceTest {
    @Autowired
    DataJpaUserRepository dataJpaUserRepository;

    @Test
    public void getWithMeals() {
        User user = dataJpaUserRepository.getWithMeal(ADMIN_ID);
        List<Meal> adminMealList = Arrays.asList(adminMeal1, adminMeal2);
        Assertions.assertThat(user.getMeals()).usingRecursiveComparison().ignoringFields("user").isEqualTo(adminMealList);

    }
}
