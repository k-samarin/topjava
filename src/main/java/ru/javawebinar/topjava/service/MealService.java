package ru.javawebinar.topjava.service;

import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.util.Collection;
import java.util.Optional;

import static ru.javawebinar.topjava.util.ValidationUtil.checkNotFoundWithId;

@Service
public class MealService {

    private final MealRepository repository;

    public MealService(MealRepository repository) {
        this.repository = repository;
    }

    public Meal create(Meal meal, int userId) {
        return Optional.ofNullable(repository.save(meal, userId))
                // It should not happen now, but probably it make sense to rise an exception if something will change
                // in case of implementation update.
                .orElseThrow(() -> new NotFoundException("Can't create a meal for user " + userId));
    }

    public Meal update(Meal meal, int userId) {
        return Optional.ofNullable(repository.save(meal, userId))
                .orElseThrow(() -> new NotFoundException("Can't update a meal for user " + userId));
    }

    public void delete(int id, int userId) {
        checkNotFoundWithId(repository.delete(id, userId), id);
    }

    public Meal get(int id, int userId) {
        return checkNotFoundWithId(repository.get(id, userId), id);
    }

    public Collection<Meal> getAll(int userId) {
        return repository.getAll(userId);
    }
}