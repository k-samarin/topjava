package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThrows;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.*;


@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {

    static {
        // Only for postgres driver logging
        // It uses java.util.logging and logged via jul-to-slf4j bridge
        SLF4JBridgeHandler.install();
    }

    @Autowired
    private MealService service;

    @Test
    public void get() {
        Meal existMeal = service.getAll(USER_ID).get(0);
        Meal meal = service.get(existMeal.getId(), USER_ID);
        AssertMatch(meal, existMeal);
    }

    @Test
    public void getNotFoundByMeal() {
        assertThrows(NotFoundException.class, () -> service.get(MEAL_ID_NOT_EXIST, USER_ID));
    }

    @Test
    public void getNotFoundByUser() {
        Meal existMeal = service.getAll(USER_ID).get(0);
        assertThrows(NotFoundException.class, () -> service.get(existMeal.getId(), NOT_FOUND));
    }

    @Test
    public void delete() {
        Meal existMeal = service.getAll(USER_ID).get(0);
        service.delete(existMeal.getId(), USER_ID);
        assertThrows(NotFoundException.class, () -> service.get(existMeal.getId(), USER_ID));
    }

    @Test
    public void deleteNotFoundByMeal() {
        assertThrows(NotFoundException.class, () -> service.delete(MEAL_ID_NOT_EXIST, USER_ID));
    }

    @Test
    public void deleteNotFoundByUser() {
        Meal existMeal = service.getAll(USER_ID).get(0);
        assertThrows(NotFoundException.class, () -> service.delete(existMeal.getId(), NOT_FOUND));
    }

    @Test
    public void getBetweenInclusive() {
        List<Meal> all = service.getBetweenInclusive(localDate_2020_01_30, localDate_2020_01_30, USER_ID);
        assertThat(all).usingRecursiveFieldByFieldElementComparatorIgnoringFields("id")
                .isEqualTo(betweenInclusiveMeals);
    }

    @Test
    public void getAll() {
        List<Meal> all = service.getAll(USER_ID);
        assertThat(all).usingRecursiveFieldByFieldElementComparatorIgnoringFields("id")
                .isEqualTo(allMeals);
    }

    @Test
    public void update() {
        Meal existMeal = service.getAll(USER_ID).get(0);

        existMeal.setDateTime(LocalDateTime.now());
        existMeal.setDescription("NewDescription");
        existMeal.setCalories(999);

        service.update(existMeal, USER_ID);

        Meal updatedMeal = service.get(existMeal.getId(), USER_ID);
        AssertMatch(updatedMeal, existMeal);
    }

    @Test
    public void updateAlien() {
        Meal newMeal = service.create(MealTestData.getNew(), ADMIN_ID);
        Meal savedMeal = service.get(newMeal.getId(), ADMIN_ID);

        savedMeal.setDateTime(LocalDateTime.now());
        savedMeal.setDescription("NewDescription");
        savedMeal.setCalories(999);

        assertThrows(NotFoundException.class, () -> service.update(savedMeal, USER_ID));
    }

    @Test
    public void create() {
        Meal newMeal = service.create(MealTestData.getNew(), USER_ID);
        Meal savedMeal = service.get(newMeal.getId(), USER_ID);
        AssertMatch(savedMeal, newMeal);
    }
}