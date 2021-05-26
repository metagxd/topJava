package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExcess;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> meals = Arrays.asList(
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
        );

        List<UserMealWithExcess> mealsTo = filteredByCycles(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        mealsTo.forEach(System.out::println);

        System.out.println(filteredByStreams(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000));
    }

    public static List<UserMealWithExcess> filteredByCycles(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        List<UserMealWithExcess> resultList = new ArrayList<>();
        Map<LocalDate, Integer> totalCaloriesByDay = new HashMap<>();
        meals.forEach((meal) ->
                totalCaloriesByDay
                        .put(meal.getDateTime().toLocalDate(), totalCaloriesByDay.getOrDefault(meal.getDateTime().toLocalDate(), 0) + meal.getCalories())
        );
        meals.forEach(meal -> {
            if (meal.getDateTime().toLocalTime().isAfter(startTime) && meal.getDateTime().toLocalTime().isBefore(endTime)) {
                resultList
                        .add(new UserMealWithExcess(meal, totalCaloriesByDay.getOrDefault(meal.getDateTime().toLocalDate(), 0) > caloriesPerDay));
            }
        });
        return resultList;
    }

    public static List<UserMealWithExcess> filteredByStreams(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> totalCaloriesByDay = new HashMap<>();
        return meals.stream()
                .peek(userMeal -> //get total sum of calories per day
                        totalCaloriesByDay.put(userMeal.getDateTime().toLocalDate(),
                                totalCaloriesByDay.getOrDefault(userMeal.getDateTime().toLocalDate(), 0)
                                        + userMeal.getCalories()))
                .filter(userMeal -> //get meals between startTime and endTime only
                        userMeal.getDateTime().toLocalTime().isAfter(startTime) && userMeal.getDateTime().toLocalTime().isBefore(endTime))
                .collect(Collectors.toList())//collect to list of userMeal
                .stream() //2nd stream
                .map(userMeal -> new UserMealWithExcess(userMeal, totalCaloriesByDay.getOrDefault(userMeal.getDateTime().toLocalDate(), 0) > caloriesPerDay)) //create userMeal with excess
                .collect(Collectors.toList()); //collect to list of userMealWithExcess
    }
}
