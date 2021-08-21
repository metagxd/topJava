package ru.javawebinar.topjava.web.meal;

import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.web.JspAbstractController;

@Controller
public class MealRestController extends JspAbstractController {

    protected MealRestController(MealService service) {
        super(service);
    }
}