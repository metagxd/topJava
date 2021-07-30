package ru.javawebinar.topjava.service;

import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import ru.javawebinar.topjava.service.datajpa.DataJpaMealServiceTest;
import ru.javawebinar.topjava.service.jdbc.JdbcMealServiceTest;
import ru.javawebinar.topjava.service.jpa.JpaMealServiceTest;
import ru.javawebinar.topjava.service.datajpa.DataJpaUserServiceTest;
import ru.javawebinar.topjava.service.jdbc.JdbcUserServiceTest;
import ru.javawebinar.topjava.service.jpa.JpaUserServiceTest;

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
