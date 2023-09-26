package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExcess;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

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
        HashMap<LocalDate, Integer> dayCalories = new HashMap();
        HashSet<UserMeal> filteredMeals = new HashSet();
        for(UserMeal meal : meals){
            LocalTime mealTime = meal.getDateTime().toLocalTime();
            if ((mealTime.compareTo(startTime) < 0) || (mealTime.compareTo(endTime) > 0)) {
                continue;
            }

            filteredMeals.add(meal);
            LocalDate mealDate = LocalDate.from(meal.getDateTime());
            dayCalories.put(mealDate, meal.getCalories() +
                (dayCalories.containsKey(mealDate) ? dayCalories.get(mealDate): 0));
        }

        List<UserMealWithExcess> mealWithExcess = new ArrayList();
        for(UserMeal meal : filteredMeals) {
            LocalDate mealDate = LocalDate.from(meal.getDateTime());
            if (dayCalories.containsKey(mealDate)) {
                mealWithExcess.add(new UserMealWithExcess(meal.getDateTime(), meal.getDescription(), meal.getCalories(), dayCalories.get(mealDate) > caloriesPerDay));
            }
        }
        return  mealWithExcess;
    }

    public static List<UserMealWithExcess> filteredByStreams(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {

        List<UserMeal> filteredMeals = meals.stream()
                .filter(m -> {
                    LocalTime mt = m.getDateTime().toLocalTime();
                    return (mt.compareTo(startTime) >= 0) && (mt.compareTo(endTime) <= 0);
                })
                .toList();

        Map<LocalDate, Integer> dayCalories = new HashMap<>();
        for (UserMeal filteredMeal : filteredMeals) {
            dayCalories.merge(filteredMeal.getDateTime().toLocalDate(), filteredMeal.getCalories(), Integer::sum);
        }

        return filteredMeals.stream()
                .map(m -> new UserMealWithExcess(m.getDateTime(), m.getDescription(), m.getCalories(), (dayCalories.get(m.getDateTime().toLocalDate()) < caloriesPerDay)))
                .toList();
    }
}
