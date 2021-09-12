package ru.javawebinar.topjava.web.meal;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.exception.NotFoundException;
import ru.javawebinar.topjava.web.AbstractControllerTest;
import ru.javawebinar.topjava.web.SecurityUtil;
import ru.javawebinar.topjava.web.json.JsonUtil;

import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.javawebinar.topjava.MealTestData.*;

class MealRestControllerTest extends AbstractControllerTest {

    String RES_MEAL_URL = MealRestController.MEAL_REST_URL + "/";

    @Autowired
    MealService mealService;

    @Test
    void getAll() throws Exception {
        perform(MockMvcRequestBuilders.get(RES_MEAL_URL))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(result -> Assertions.assertEquals(mealTos,
                        JsonUtil.readValues(result.getResponse().getContentAsString(), MealTo.class)));
    }

    @Test
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(RES_MEAL_URL + MEAL1_ID))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertThrows(NotFoundException.class, () -> mealService.get(MEAL1_ID, SecurityUtil.authUserId()));
    }

    @Test
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(RES_MEAL_URL + MEAL1_ID))
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MATCHER.contentJson(meal1));
    }

    @Test
    void createWithLocation() throws Exception {
        perform(MockMvcRequestBuilders.post(RES_MEAL_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(MealTestData.getNew())))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    void update() throws Exception {
        perform(MockMvcRequestBuilders.put(RES_MEAL_URL + MEAL1_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(MealTestData.getUpdated())))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    void getBetween() throws Exception {
        String startTime = "14:00";

        List<MealTo> tos = MealsUtil.filterByPredicate(meals,
                MealsUtil.DEFAULT_CALORIES_PER_DAY,
                m -> m.getDateTime().toLocalTime().isAfter(LocalTime.parse(startTime)));

        perform(MockMvcRequestBuilders.get(RES_MEAL_URL + "filter")
                .queryParam("startTime", startTime)
                .queryParam("endTime", ""))
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(result -> Assertions.assertEquals(tos,
                        JsonUtil.readValues(result.getResponse().getContentAsString(), MealTo.class)));
    }
}