package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Controller
public class MealRestController {
    protected final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private MealService service;


    public List<MealTo> getAll(int userId) {
        log.info("getAll");

        List<Meal> mealList = service.getAll(userId);
        return MealsUtil.getTos(mealList, MealsUtil.DEFAULT_CALORIES_PER_DAY);
    }

    public List<MealTo> getFilteredTos(int caloriesPerDay, LocalTime startTime, LocalTime endTime, LocalDate startDate, LocalDate endDate, int userId) {
        log.info("getFilteredTos");

        List<Meal> mealList = service.getAll(userId);
        return MealsUtil.getFilteredTos(mealList, caloriesPerDay, startTime, endTime, startDate, endDate);
    }

    public Meal get(int id, int userId) {
        log.info("get");

        return service.get(id, userId);
    }

    public Meal create(Meal meal, int userId) {
        log.info("create");

        return service.create(meal, userId);
    }

    public void delete(int id, int userId) {
        log.info("delete");

        service.delete(id, userId);
    }

    public void update(Meal meal, int userId) {
        log.info("update");

        service.update(meal, userId);
    }


}