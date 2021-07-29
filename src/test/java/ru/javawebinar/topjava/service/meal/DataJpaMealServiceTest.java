package ru.javawebinar.topjava.service.meal;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.springframework.aop.framework.Advised;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.datajpa.DataJpaMealRepository;

import static ru.javawebinar.topjava.MealTestData.ADMIN_MEAL_ID;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;
import static ru.javawebinar.topjava.UserTestData.admin;

@ActiveProfiles("datajpa")
public class DataJpaMealServiceTest extends AbstractMealServiceTest {
    @Autowired
    ApplicationContext applicationContext;

    @Test
    public void getWithUser() throws Exception {
        DataJpaMealRepository dataJpaMealRepository = null;
        Object proxyRepo = applicationContext.getBean("dataJpaMealRepository");
        if (AopUtils.isAopProxy(proxyRepo) && proxyRepo instanceof Advised) {
            Object target = ((Advised) proxyRepo).getTargetSource().getTarget();
            dataJpaMealRepository = (DataJpaMealRepository) target;
        }

        assert dataJpaMealRepository != null;
        Meal mealWithUser = dataJpaMealRepository.getWithUser(ADMIN_MEAL_ID, ADMIN_ID);
        Assertions.assertThat(mealWithUser.getUser()).usingRecursiveComparison().ignoringFields("meals", "registered").isEqualTo(admin);
    }
}
