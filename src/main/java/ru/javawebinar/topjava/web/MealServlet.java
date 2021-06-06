package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.repository.InMemoryMealRepository;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

public class MealServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(MealServlet.class);
    private final MealRepository mealRepository = new InMemoryMealRepository();
    private final int SUM_OF_CALORIES = 2000;

    @Override
    public void init() throws ServletException {
        List<Meal> meals = Arrays.asList(
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
                new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
        );
        meals.forEach(mealRepository::save);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");
        request.setAttribute("action", action);
        switch (action == null ? "" : action) {
            case "delete":
                int mealId = Integer.parseInt(request.getParameter("mealId"));
                log.debug("delete {}", mealId);
                mealRepository.delete(mealId);
                response.sendRedirect("meals");
                break;
            case "edit":
                mealId = Integer.parseInt(request.getParameter("mealId"));
                Meal meal = mealRepository.getById(mealId);
                log.debug("edit {}", mealId);
                request.setAttribute("meal", meal);
                request.getRequestDispatcher("/edit.jsp").forward(request, response);
                break;
            case "create":
                Meal meal1 = new Meal(LocalDateTime.now().withNano(0).withSecond(0), "", 0);
                log.debug("create new meal");
                request.setAttribute("meal", meal1);
                request.getRequestDispatcher("/edit.jsp").forward(request, response);
                break;
            default:
                log.debug("response with meal list");
                List<MealTo> mealToList = MealsUtil.filteredByStreams(mealRepository.getAll(), LocalTime.of(0, 0), LocalTime.of(23, 59), SUM_OF_CALORIES);
                request.setAttribute("mealList", mealToList);
                request.getRequestDispatcher("/meals.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        if ("cancel".equals(request.getParameter("cancelButton"))) {
            response.sendRedirect("meals");
            return;
        }
        int id = Integer.parseInt(request.getParameter("id"));
        LocalDateTime dateTime = LocalDateTime.parse(request.getParameter("dateTime"));
        String description = request.getParameter("description");
        int calories = Integer.parseInt(request.getParameter("calories"));
        Meal meal = new Meal(dateTime, description, calories);
        if (id == 0) {
            log.debug("creating new meal");
            mealRepository.save(meal);
        } else {
            log.debug("updating {}", id);
            meal.setId(id);
            mealRepository.update(meal);
        }
        response.sendRedirect("meals");
    }
}
