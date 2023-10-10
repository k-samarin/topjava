package ru.javawebinar.topjava.model;

import java.time.LocalDateTime;
import java.util.List;

public interface MealUpdater {
    void add(LocalDateTime dateTime, String description, int calories);

    boolean update(int id, LocalDateTime dateTime, String description, int calories);

    boolean remove(int id);

    List<Meal> getMeals();

    Meal getMealById(int id);
}
