package ru.javawebinar.topjava.web.meal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;

import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalDate;
import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalTime;

@Controller
public class JspMealController {
    @Autowired
    private MealService mealService;

    @GetMapping("/meals")
    String getAll(Model model) {
        List<MealTo> mealTos = MealsUtil.getTos(mealService.getAll(SecurityUtil.authUserId()), SecurityUtil.authUserCaloriesPerDay());
        model.addAttribute("meals", mealTos);
        return "meals";
    }

    @GetMapping("/meals/delete")
    String delete(HttpServletRequest request) {
        mealService.delete(getId(request), SecurityUtil.authUserId());
        return "redirect:/meals";
    }

    @GetMapping("/meals/update")
    String update(HttpServletRequest request, Model model) {
        Meal meal = mealService.get(getId(request), SecurityUtil.authUserId());
        model.addAttribute("action", "update");
        model.addAttribute("meal", meal);
        return "mealForm";
    }

    @PostMapping("/meals/update")
    String update(HttpServletRequest request) {
        Meal meal = new Meal(
                getId(request),
                LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.parseInt(request.getParameter("calories")));
        mealService.update(meal, SecurityUtil.authUserId());
        return "redirect:/meals";
    }

    @GetMapping("/meals/create")
    String create(Model model) {
        Meal meal = new Meal(
                LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES),
                "",
                1000);
        model.addAttribute("meal", meal);
        model.addAttribute("action", "create");
        return "mealForm";
    }

    @PostMapping("/meals/create")
    String create(HttpServletRequest request) {
        Meal meal = new Meal(
                LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.parseInt(request.getParameter("calories")));
        mealService.create(meal, SecurityUtil.authUserId());
        return "redirect:/meals";
    }

    @PostMapping("/meals")
    String getFiltered(HttpServletRequest request, Model model) {
        LocalDate startDate = parseLocalDate(request.getParameter("startDate"));
        LocalDate endDate = parseLocalDate(request.getParameter("endDate"));
        LocalTime startTime = parseLocalTime(request.getParameter("startTime"));
        LocalTime endTime = parseLocalTime(request.getParameter("endTime"));
        List<Meal> mealsDateFiltered = mealService.getBetweenInclusive(startDate, endDate, SecurityUtil.authUserId());
        List<MealTo> filteredTos = MealsUtil.getFilteredTos(mealsDateFiltered, SecurityUtil.authUserCaloriesPerDay(), startTime, endTime);
        model.addAttribute("meals", filteredTos);
        return "meals";
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.parseInt(paramId);
    }
}
