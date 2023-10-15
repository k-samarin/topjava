package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.util.Collection;

@Controller
public class MealRestController {

    private int userId = SecurityUtil.authUserId();

    protected final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private MealService service;


    public Meal create(Meal meal) {
        return service.create(meal, userId);
    }

    public Meal update(Meal meal) {
        return service.update(meal, userId);
    }

    public void delete(int id) {
        service.delete(id, userId);
    }

    public Meal get(int id) {
        return service.get(id, userId);
    }

    public Collection<Meal> getAll() {
        return service.getAll(userId);
    }
}