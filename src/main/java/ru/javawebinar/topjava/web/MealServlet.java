package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

import static org.slf4j.LoggerFactory.getLogger;
import static ru.javawebinar.topjava.util.MealsUtil.filteredByStreams;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("redirect to meals");

        List<Meal> meals = MealsUtil.getMeals();
        List<MealTo> mealsTo = filteredByStreams(meals, LocalTime.of(0, 0), LocalTime.of(23, 59), 2000);
        request.setAttribute("meals", mealsTo);

        getServletContext().getRequestDispatcher("/meals.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.debug("meal post");

        List<Meal> meals = MealsUtil.getMeals();
        String date = req.getParameter("date");
        String description = req.getParameter("description");
        String calories = req.getParameter("calories");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH);
        LocalDate localDate = LocalDate.parse(date, formatter);
        LocalDateTime localDateTime = localDate.atStartOfDay();
        int cal = Integer.parseInt(calories);
        Meal meal = new Meal(localDateTime, description, cal);
        meals.add(meal);
        MealsUtil.setMeals(meals);

        doGet(req, resp);
    }
}
