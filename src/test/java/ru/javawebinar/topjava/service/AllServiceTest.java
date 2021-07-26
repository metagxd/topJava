package ru.javawebinar.topjava.service;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        DataJpaMealServiceTest.class,
        JdbcMealServiceTest.class,
        JpaMealServiceTest.class,
        DataJpaUserServiceTest.class,
        JdbcUserServiceTest.class,
        JpaUserServiceTest.class
})
public class AllServiceTest {
}
