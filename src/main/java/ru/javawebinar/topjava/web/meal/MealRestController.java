package ru.javawebinar.topjava.web.meal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.to.MealTo;

import java.net.URI;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping(value = MealRestController.MEAL_REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class MealRestController extends AbstractMealController {
    static final String MEAL_REST_URL = "/rest/meals";

    @Autowired(required = false)
    ConversionService conversionService;


    @Override
    @GetMapping
    public List<MealTo> getAll() {
        return super.getAll();
    }

    @Override
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        super.delete(id);
    }

    @Override
    @GetMapping("/{id}")
    public Meal get(@PathVariable int id) {
        return super.get(id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Meal> createWithLocation(@RequestBody Meal meal) {
        Meal created = super.create(meal);
        URI uriOfNewResource = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path(MEAL_REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(Meal meal, @PathVariable int id) {
        super.update(meal, id);
    }

    @GetMapping("/filter")
    public List<MealTo> getBetween(@RequestParam MultiValueMap<String, String> params) {
        LocalDate parsedStartDate = getConvertedOrNull(params, "startDate", LocalDate.class);
        LocalDate parsedEndDate = getConvertedOrNull(params, "endDate", LocalDate.class);
        LocalTime parsedStartTime = getConvertedOrNull(params, "startTime", LocalTime.class);
        LocalTime parsedEndTime = getConvertedOrNull(params, "endTime", LocalTime.class);

        return super.getBetween(parsedStartDate, parsedStartTime, parsedEndDate, parsedEndTime);
    }

    private <T> T getConvertedOrNull(MultiValueMap<String, String> params, String paramName, Class<T> tClass) {
        String value = params.getFirst(paramName);
        if (value == null || value.isEmpty()) {
            return null;
        }
        return conversionService.convert(value, tClass);
    }
}