package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public class DeleteMealServlet extends HttpServlet {
    private static final Logger log = getLogger(DeleteMealServlet.class);
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
        log.debug("delete get");

        final String idString = req.getParameter("id");
        int id;
        try {
            id = Integer.parseInt(idString);
        }
        catch (NumberFormatException e) {
            throw new NumberFormatException();
        }

        for (Meal m: meals) {
            if (m.getId() == id) {
                meals.remove(m);
                break;
            }
        }

        resp.sendRedirect("/topjava/meals");
    }
}
