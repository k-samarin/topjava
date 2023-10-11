package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public interface MealRepository {
    Meal add(Meal meal);

    Meal update(Meal meal);

    void remove(int id);

    List<Meal> getAll();

    Meal getById(int id);
}
