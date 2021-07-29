package ru.javawebinar.topjava.service;

import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import ru.javawebinar.topjava.service.meal.DataJpaMealServiceTest;
import ru.javawebinar.topjava.service.meal.JdbcMealServiceTest;
import ru.javawebinar.topjava.service.meal.JpaMealServiceTest;
import ru.javawebinar.topjava.service.user.DataJpaUserServiceTest;
import ru.javawebinar.topjava.service.user.JdbcUserServiceTest;
import ru.javawebinar.topjava.service.user.JpaUserServiceTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        DataJpaMealServiceTest.class,
        JdbcMealServiceTest.class,
        JpaMealServiceTest.class,
        DataJpaUserServiceTest.class,
        JdbcUserServiceTest.class,
        JpaUserServiceTest.class
})
@Ignore
public class AllServiceTest {
}
