package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExcess;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.chrono.ChronoLocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> meals = Arrays.asList(
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
        );

        List<UserMealWithExcess> mealsTo = filteredByCycles(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        mealsTo.forEach(System.out::println);

//        System.out.println(filteredByStreams(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000));
    }

    public static List<UserMealWithExcess> filteredByCycles(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {

        List<UserMealWithExcess> returningList = new ArrayList<>();
        Map<Integer, Boolean> daysWithExcessMap = new HashMap<>();
        List<UserMeal> entryMeals = new ArrayList<>();
        int eatenCalories = 0;
        int dayOfYear = meals.get(0).getDateTime().getDayOfYear();
        meals.sort((o1, o2) -> o1.getDateTime().getDayOfYear() - o2.getDateTime().getDayOfYear());

        for (UserMeal userMeal : meals) {
            if (userMeal.getDateTime().getDayOfYear() != dayOfYear) {
                dayOfYear = userMeal.getDateTime().getDayOfYear();
                eatenCalories = 0;
            }
            eatenCalories += userMeal.getCalories();
            if (eatenCalories > caloriesPerDay) {
                daysWithExcessMap.put(dayOfYear, true);
            }
            if (userMeal.getDateTime().getHour() >= startTime.getHour() && userMeal.getDateTime().getHour() <= endTime.getHour()) {
                entryMeals.add(userMeal);
            }
        }

        for (UserMeal userMeal : entryMeals) {
            int dayOfYear1 = userMeal.getDateTime().getDayOfYear();
            returningList.add(new UserMealWithExcess(userMeal.getDateTime(), userMeal.getDescription(), userMeal.getCalories(), daysWithExcessMap.getOrDefault(dayOfYear1, false)));

        }

        return returningList;
    }

    public static List<UserMealWithExcess> filteredByStreams(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        // TODO Implement by streams
        return null;
    }
}
