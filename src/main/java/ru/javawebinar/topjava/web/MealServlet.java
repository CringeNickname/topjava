package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.meal.MealRestController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;

public class MealServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(MealServlet.class);

    private ConfigurableApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml");
    private MealRestController controller;

    @Override
    public void init() {
        controller = appCtx.getBean(MealRestController.class);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String id = request.getParameter("id");

        Meal meal = new Meal(id.isEmpty() ? null : Integer.valueOf(id),
                LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.parseInt(request.getParameter("calories")));

        log.info(meal.isNew() ? "Create {}" : "Update {}", meal);
        controller.create(meal, LoggedUser.getId());
        response.sendRedirect("meals");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        switch (action == null ? "all" : action) {
            case "delete":
                int id = getId(request);
                log.info("Delete id={}", id);
                controller.delete(id, LoggedUser.getId());
                response.sendRedirect("meals");
                break;
            case "create":
            case "update":
                final Meal meal = "create".equals(action) ?
                        new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000) :
                        controller.get(getId(request), LoggedUser.getId());
                request.setAttribute("meal", meal);
                request.getRequestDispatcher("/mealForm.jsp").forward(request, response);
                break;
            case "filter":
                String stringStartDate = request.getParameter("startDate");
                LocalDate startDate;
                if (stringStartDate.equals("")) {
                    startDate = LocalDate.MIN;
                }
                else {
                    startDate = LocalDate.parse(stringStartDate);
                }

                String stringEndDate = request.getParameter("endDate");
                LocalDate endDate;
                if (stringEndDate.equals("")) {
                    endDate = LocalDate.MAX;
                }
                else {
                    endDate = LocalDate.parse(stringEndDate);
                }

                String stringStartTime = request.getParameter("startTime");
                LocalTime startTime;
                if (stringStartTime.equals("")) {
                    startTime = LocalTime.MIN;
                }
                else {
                    startTime = LocalTime.parse(stringStartTime);
                }

                String stringEndTime = request.getParameter("endTime");
                LocalTime endTime;
                if (stringEndTime.equals("")) {
                    endTime = LocalTime.MAX;
                }
                else {
                    endTime = LocalTime.parse(stringEndTime);
                }

                List<MealTo> mealTos = controller.getFilteredTos(MealsUtil.DEFAULT_CALORIES_PER_DAY, startTime, endTime, startDate, endDate, LoggedUser.getId());

                request.setAttribute("meals", mealTos);
                request.getRequestDispatcher("/meals.jsp").forward(request, response);
                break;
            case "all":
            default:
                log.info("getAll");
                request.setAttribute("meals",
                        controller.getAll(LoggedUser.getId()));
                request.getRequestDispatcher("/meals.jsp").forward(request, response);
                break;
        }
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.parseInt(paramId);
    }
}
