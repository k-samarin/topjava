package ru.javawebinar.topjava.repository.inmemory;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepository implements MealRepository {
    private final Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.mealsJenuaryUser2.forEach(meal -> save(meal, meal.getUserId()));
        MealsUtil.mealsJenuaryUser1.forEach(meal -> save(meal, meal.getUserId()));
        MealsUtil.mealsFebruaryUser2.forEach(meal -> save(meal, meal.getUserId()));
    }

    @Override
    public Meal save(Meal meal, int userId) {
        meal.setUserId(userId);
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            repository.put(meal.getId(), meal);
            return meal;
        }

        return get(meal.getId(), userId) == null
                ? null
                : repository.computeIfPresent(meal.getId(), (k, oldMeal) -> meal);
    }

    @Override
    public boolean delete(int id, int userId) {
        Meal meal = repository.get(id);
        return meal != null && meal.getUserId() == userId && repository.remove(id) != null;
    }

    @Override
    public Meal get(int id, int userid) {
        Meal meal = repository.get(id);
        return meal != null && meal.getUserId() == userid ? meal : null;
    }

    @Override
    public List<Meal> getAll(int userId) {
        return getFiltered(meal -> isForUser(meal.getUserId(), userId));
    }

    @Override
    public List<Meal> getFiltered(int userId, LocalDate startDate, LocalDate endDate) {
        return getFiltered(meal -> isForUserAndBetweenDatesClosed(meal.getUserId(), userId, meal.getDate(), startDate, endDate));
    }

    private List<Meal> getFiltered(Predicate<Meal> filter) {
        return repository.values()
                .stream()
                .filter(filter)
                .sorted(Comparator.comparing(Meal::getDateTime).reversed())
                .collect(Collectors.toList());

    }

    private boolean isForUserAndBetweenDatesClosed(int mealUserId, int userId, LocalDate ld, LocalDate startDate, LocalDate endDate) {
        return mealUserId == userId && !ld.isBefore(startDate) && !ld.isAfter(endDate);
    }

    private boolean isForUser(int mealUserId, int userId) {
        return mealUserId == userId;
    }
}

