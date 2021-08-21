package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;
import ru.javawebinar.topjava.util.ValidationUtil;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

@Repository
@Transactional(readOnly = true)
public class JdbcUserRepository implements UserRepository {

    private static final BeanPropertyRowMapper<User> ROW_MAPPER = BeanPropertyRowMapper.newInstance(User.class);

    private final JdbcTemplate jdbcTemplate;

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final SimpleJdbcInsert insertUser;

    @Autowired
    public JdbcUserRepository(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.insertUser = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("users")
                .usingGeneratedKeyColumns("id");

        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    @Transactional
    public User save(User user) {
        System.out.println(TransactionSynchronizationManager.isActualTransactionActive());
        ValidationUtil.validate(user);
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource(user);
        if (user.isNew()) {
            Number newKey = insertUser.executeAndReturnKey(parameterSource);
            user.setId(newKey.intValue());
        } else if (namedParameterJdbcTemplate.update("""
                   UPDATE users SET name=:name, email=:email, password=:password,
                   registered=:registered, enabled=:enabled, calories_per_day=:caloriesPerDay WHERE id=:id
                """, parameterSource) == 0 && insertRoles(user.id(), user.getRoles()) == 0) {
            return null;
        }
        insertRoles(user.id(), user.getRoles());
        return user;
    }

    @Override
    @Transactional
    public boolean delete(int id) {
        return jdbcTemplate.update("DELETE FROM users WHERE id=?", id) != 0;
    }

    @Override
    public User get(int id) {
        List<User> users = jdbcTemplate.query("SELECT * FROM users WHERE id=?", ROW_MAPPER, id);
        User user = DataAccessUtils.singleResult(users);
        if (user == null) {
            return null;
        }
        user.setRoles(getRoles(user.id()));
        return user;
    }

    @Override
    public User getByEmail(String email) {
//        return jdbcTemplate.queryForObject("SELECT * FROM users WHERE email=?", ROW_MAPPER, email);
        List<User> users = jdbcTemplate.query("SELECT * FROM users WHERE email=?", ROW_MAPPER, email);
        User user = DataAccessUtils.singleResult(users);
        assert user != null;
        user.setRoles(getRoles(user.id()));
        return user;
    }

    @Override
    public List<User> getAll() {
        return jdbcTemplate.query("SELECT u.id, u.name, u.email, u.password, u.registered, u.enabled, u.calories_per_day," +
                "string_agg(ur.role, ', ')  AS roles FROM users u LEFT JOIN user_roles ur ON u.id = ur.user_id GROUP BY " +
                "u.id ORDER BY u.name, u.email", ROW_MAPPER);
    }

    private int insertRoles(int userId, Set<Role> roles) {
        removeRoles(userId);
        List<Role> rolesList = new ArrayList<>(roles);
        int[] rows = jdbcTemplate.batchUpdate("INSERT INTO user_roles (user_id, role) values (?, ?)", new BatchPreparedStatementSetter() {
            @Override
            public void setValues(@NonNull PreparedStatement ps, int i) throws SQLException {
                ps.setInt(1, userId);
                ps.setString(2, rolesList.get(i).toString());
            }

            @Override
            public int getBatchSize() {
                return rolesList.size();
            }
        });
        return Arrays.stream(rows).sum();
    }

    private int removeRoles(int userId) {
        return jdbcTemplate.update("DELETE FROM user_roles WHERE user_id=?", userId);
    }

    private List<Role> getRoles(int userId) {
        return jdbcTemplate.query("SELECT * FROM user_roles WHERE user_id = ?", rs -> {
            List<Role> roleList = new ArrayList<>();
            while (rs.next()) {
                roleList.add(Role.valueOf(rs.getString("role")));
            }
            return roleList;
        }, userId);
    }
}
