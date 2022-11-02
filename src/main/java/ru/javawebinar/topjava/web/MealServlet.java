package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalTime;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;
import static ru.javawebinar.topjava.util.MealsUtil.filteredByStreams;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);
    private List<Meal> meals;

    @Override
    public void init() throws ServletException {
        final Object mealsDatabase = getServletContext().getAttribute("mealsDatabase");

        if (mealsDatabase == null || !(mealsDatabase instanceof List)) {
            throw new IllegalArgumentException("Data is not initialized!");
        }
        else {
            this.meals = (List<Meal>) mealsDatabase;
            log.debug(meals.toString());
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("redirect to meals");


        List<MealTo> mealsTo = filteredByStreams(meals, LocalTime.of(0, 0), LocalTime.of(23, 59), 2000);
        request.setAttribute("meals", mealsTo);
        log.debug(meals.toString());

        getServletContext().getRequestDispatcher("/meals.jsp").forward(request, response);
    }
}
