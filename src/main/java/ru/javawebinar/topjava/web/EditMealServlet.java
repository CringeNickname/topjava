package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import static org.slf4j.LoggerFactory.getLogger;

public class EditMealServlet extends HttpServlet {
    private static final Logger log = getLogger(EditMealServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.debug("redirect to editMeal");


        int id = Integer.parseInt(req.getParameter("id"));
        req.setAttribute("id", id);

        getServletContext().getRequestDispatcher("/editMeal.jsp").forward(req, resp);
    }
}
