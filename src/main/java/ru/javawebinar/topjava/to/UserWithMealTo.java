package ru.javawebinar.topjava.to;

import ru.javawebinar.topjava.model.User;

import java.util.List;

public class UserWithMealTo {
    private final User user;

    private final List<MealTo> meals;

    public UserWithMealTo(User user, List<MealTo> meals){
        this.user = user;
        this.meals = meals;
    }
}
