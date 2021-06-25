package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class JdbcMealRepository implements MealRepository {

    private static final BeanPropertyRowMapper<Meal> ROW_MAPPER = BeanPropertyRowMapper.newInstance(Meal.class);

    private final JdbcTemplate jdbcTemplate;

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final SimpleJdbcInsert insertMeal;

    @Autowired
    public JdbcMealRepository(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.insertMeal = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("meals")
                .usingGeneratedKeyColumns("id");
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public Meal save(Meal meal, int userId) {

        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource()
                .addValue("id", meal.getId())
                .addValue("user_id", userId)
                .addValue("date_time", Timestamp.valueOf(meal.getDateTime()))
                .addValue("description", meal.getDescription())
                .addValue("calories", meal.getCalories());
        if (meal.isNew()) {
            Number newKey = insertMeal.executeAndReturnKey(mapSqlParameterSource);
            meal.setId(newKey.intValue());
            return meal;
        } else if (
                namedParameterJdbcTemplate.update("UPDATE meals SET date_time=:date_time, description=:description," +
                        " calories=:calories WHERE id=:id", mapSqlParameterSource) == 0) {
            return null;
        }
        return meal;
    }

    @Override
    public boolean delete(int id, int userId) {
        String sql = "DELETE FROM meals WHERE (id, user_id) = (?, ?)";
        return jdbcTemplate.update(sql, id, userId) > 0;
    }

    @Override
    public Meal get(int id, int userId) {
        String sql = "SELECT * FROM meals WHERE (id, user_id) = (?,?)";
        List<Meal> meals = jdbcTemplate.query(sql, ROW_MAPPER, id, userId);
        return DataAccessUtils.singleResult(meals);
    }

    @Override
    public List<Meal> getAll(int userId) {
        String sql = "SELECT * FROM meals WHERE user_id = ? ORDER BY date_time DESC";
        return jdbcTemplate.query(sql, ROW_MAPPER, userId);
    }

    @Override
    public List<Meal> getBetweenHalfOpen(LocalDateTime startDateTime, LocalDateTime endDateTime, int userId) {
        String sql = "SELECT * FROM meals WHERE user_id = ? AND (date_time > ?) AND (date_time <=?) ORDER BY date_time DESC ";
        return jdbcTemplate.query(sql, ROW_MAPPER, userId, Timestamp.valueOf(startDateTime),
                Timestamp.valueOf(endDateTime));
    }
}
