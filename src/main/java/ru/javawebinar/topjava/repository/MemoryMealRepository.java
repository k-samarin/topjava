package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class MemoryMealRepository implements MealRepository {
    private final AtomicInteger idCounter = new AtomicInteger(0);
    private final Map<Integer, Meal> meals = new ConcurrentHashMap<>();

    public MemoryMealRepository() {
        // Test collection initialisation.
        List<Meal> testMeals = Arrays.asList(
                new Meal(null, LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
                new Meal(null, LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
                new Meal(null, LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
                new Meal(null, LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
                new Meal(null, LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
                new Meal(null, LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
                new Meal(null, LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
        );

        for (Meal m : testMeals) {
            add(m);
        }
    }

    @Override
    public Meal add(Meal meal) {
        Meal m = new Meal(idCounter.incrementAndGet(), meal.getDateTime(), meal.getDescription(), meal.getCalories());
        meals.put(m.getId(), m);
        return new Meal(m);
    }

    @Override
    public Meal update(Meal meal) {
        return meals.replace(meal.getId(), new Meal(meal)) == null
                ? null
                : new Meal(meal);
    }

    @Override
    public void remove(int id) {
        meals.remove(id);
    }

    @Override
    public List<Meal> getAll() {
        return new ArrayList<>(meals.values());
    }

    @Override
    public Meal getById(int id) {
        Meal m = meals.get(id);
        return new Meal(m.getId(), m.getDateTime(), m.getDescription(), m.getCalories());
    }
}
