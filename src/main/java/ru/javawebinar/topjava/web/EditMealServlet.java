package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

import static org.slf4j.LoggerFactory.getLogger;

public class EditMealServlet extends HttpServlet {
    private static final Logger log = getLogger(EditMealServlet.class);
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
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.debug("editMeal get");

        final String idString = req.getParameter("id");
        if (idString != null) {
            req.setAttribute("id", idString);
        }

        getServletContext().getRequestDispatcher("/editMeal.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.debug("editMeal post");

        req.setCharacterEncoding("UTF-8");

        final String idString = req.getParameter("id");

        String date = req.getParameter("date").replace("T", " ");
        String description = req.getParameter("description");
        String calories = req.getParameter("calories");

        int cal = Integer.parseInt(calories);

        if (idString != null && !idString.equals("")) { // Editing meal
            int id;
            try {
                id = Integer.parseInt(idString);
            }
            catch (NumberFormatException e) {
                throw new NumberFormatException();
            }
            Meal editedMeal = new Meal(id, LocalDateTime.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm", Locale.ENGLISH)), description, cal);
            for (Meal m: meals) {
                if (m.getId() == id) {
                    meals.remove(m);
                    break;
                }
            }
            meals.add(editedMeal);

            resp.sendRedirect("/topjava/meals");
        }
        else {  // Adding new meal
            Meal meal = new Meal(meals.size(), LocalDateTime.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm", Locale.ENGLISH)), description, cal);
            meals.add(meal);

            resp.sendRedirect("/topjava/meals");
        }
    }
}
