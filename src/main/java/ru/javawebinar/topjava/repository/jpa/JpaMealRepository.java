package ru.javawebinar.topjava.repository.jpa;

import org.springframework.dao.support.DataAccessUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class JpaMealRepository implements MealRepository {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    @Transactional
    public Meal save(Meal meal, int userId) {
        User user;
        try {
            user = entityManager.getReference(User.class, userId);
        } catch (EntityNotFoundException e) {
            throw new NotFoundException("User " + userId + " not found!");
        }
        if (meal.isNew()) {
            meal.setUser(user);
            entityManager.persist(meal);
            return meal;
        } else {
            if (entityManager.getReference(Meal.class, meal.getId()).getUser().getId() != userId) {
                return null;
            }
            meal.setUser(user);
            return entityManager.merge(meal);
        }
    }

    @Override
    @Transactional
    public boolean delete(int id, int userId) {
        return entityManager.createNamedQuery(Meal.DELETE)
                .setParameter("id", id)
                .setParameter("user", entityManager.getReference(User.class, userId))
                .executeUpdate() != 0;
    }

    @Override
    @Transactional(readOnly = true)
    public Meal get(int id, int userId) {
        List<Meal> resultList = entityManager.createNamedQuery(Meal.GET, Meal.class)
                .setParameter("id", id)
                .setParameter("userId", userId)
                .getResultList();
        return DataAccessUtils.singleResult(resultList);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Meal> getAll(int userId) {
        return entityManager.createNamedQuery(Meal.GET_ALL, Meal.class)
                .setParameter("userId", userId).getResultList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Meal> getBetweenHalfOpen(LocalDateTime startDateTime, LocalDateTime endDateTime, int userId) {
        return entityManager.createNamedQuery(Meal.GET_ALL_SORTED, Meal.class)
                .setParameter("startDateTime", startDateTime)
                .setParameter("endDateTime", endDateTime)
                .setParameter("userId", userId).getResultList();
    }
}