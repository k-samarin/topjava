package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExcess;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;

import static java.util.stream.Collectors.*;

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

        System.out.println(filteredByStreams(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000));
    }

    public static List<UserMealWithExcess> filteredByCycles(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> dayCalories = new HashMap<>();
        List<UserMeal> filteredMeals = new ArrayList<>();
        for(UserMeal meal : meals) {
            if (TimeUtil.isBetweenHalfOpen(meal.getDateTime().toLocalTime(), startTime, endTime)) {
                filteredMeals.add(meal);
            }
            LocalDate mealDate = meal.getDateTime().toLocalDate();
            dayCalories.put(mealDate, meal.getCalories() + dayCalories.getOrDefault(mealDate, 0));
        }

        List<UserMealWithExcess> mealWithExcess = new ArrayList<>();
        for(UserMeal meal : filteredMeals) {
            LocalDate mealDate = meal.getDateTime().toLocalDate();
            mealWithExcess.add(new UserMealWithExcess(meal.getDateTime(), meal.getDescription(), meal.getCalories(), dayCalories.get(mealDate) > caloriesPerDay));
        }
        return  mealWithExcess;
    }

    public static List<UserMealWithExcess> filteredByStreams(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {

        List<UserMeal> filteredMeals = meals.stream()
                .filter(m -> TimeUtil.isBetweenHalfOpen(m.getDateTime().toLocalTime(), startTime, endTime))
                .collect(toList());

        Map<LocalDate, Integer> dayCalories = meals.stream()
                .collect(toMap(meal -> meal.getDateTime().toLocalDate(), UserMeal::getCalories, Integer::sum));

        return filteredMeals.stream()
                .map(m -> new UserMealWithExcess(m.getDateTime(), m.getDescription(), m.getCalories(), (dayCalories.get(m.getDateTime().toLocalDate()) > caloriesPerDay)))
                .collect(toList());
    }
}
