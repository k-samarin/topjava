package ru.javawebinar.topjava.model;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class MemoryMealUpdater implements MealUpdater {
    private static final AtomicInteger idCounter = new AtomicInteger(-1);

    private static final List<Meal> meals = Collections.synchronizedList(new ArrayList<>());

    private MemoryMealUpdater() {
        // Test collection initialisation.
        meals.add(new Meal(idCounter.incrementAndGet(),
                LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500));
        meals.add(new Meal(idCounter.incrementAndGet(),
                LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000));
        meals.add(new Meal(idCounter.incrementAndGet(),
                LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500));
        meals.add(new Meal(idCounter.incrementAndGet(),
                LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100));
        meals.add(new Meal(idCounter.incrementAndGet(),
                LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000));
        meals.add(new Meal(idCounter.incrementAndGet(),
                LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500));
        meals.add(new Meal(idCounter.incrementAndGet(),
                LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410));
    }

    private static class SingletonHelper {
        private static final MemoryMealUpdater INSTANCE = new MemoryMealUpdater();
    }

    public static MemoryMealUpdater getInstance() {
        return SingletonHelper.INSTANCE;
    }

    @Override
    public void add(LocalDateTime dateTime, String description, int calories) {
        Meal m = new Meal(idCounter.incrementAndGet(), dateTime, description, calories);
        meals.add(m);
    }

    @Override
    public boolean update(int id, LocalDateTime dateTime, String description, int calories) {
        List<Meal> filteredMeal = meals.stream()
                .filter(m -> m.getId() == id)
                .collect(Collectors.toList());
        if (filteredMeal.size() == 1) {
            Meal m = filteredMeal.get(0);
            synchronized (m) {
                m.setDateTime(dateTime);
                m.setDescription(description);
                m.setCalories(calories);
            }
        } else {
            return false;
        }
        return true;
    }

    @Override
    public boolean remove(int id) {
        int countOfMeals = meals.size();
        List<Meal> filteredMeal = meals.stream()
                .filter(m -> m.getId() == id)
                .collect(Collectors.toList());
        if (filteredMeal.size() == 1) {
            Meal m = filteredMeal.get(0);
            meals.remove(m);
        }
        return countOfMeals == meals.size();
    }

    @Override
    public List<Meal> getMeals() {
        return meals;
    }

    @Override
    public Meal getMealById(int id) {
        List<Meal> filteredMeal = meals.stream()
                .filter(m -> m.getId() == id)
                .collect(Collectors.toList());
        if (filteredMeal.size() == 1) {
            return filteredMeal.get(0);
        }
        return null;
    }
}
