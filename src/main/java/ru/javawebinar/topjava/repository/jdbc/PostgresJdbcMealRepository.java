package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.repository.MealRepository;

import java.time.LocalDateTime;

@Repository
@Profile("postgres")
public class PostgresJdbcMealRepository extends AbstractJdbcMealRepository implements MealRepository {
    public PostgresJdbcMealRepository(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        super(jdbcTemplate, namedParameterJdbcTemplate);
    }

    @Override
    <T> T getDateTime(LocalDateTime dateTime) {
        return (T) dateTime;
    }
}
