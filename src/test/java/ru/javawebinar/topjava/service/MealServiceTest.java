package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.util.List;

import static org.junit.Assert.assertThrows;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;
import static ru.javawebinar.topjava.UserTestData.USER_ID;


@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-repo.xml",
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
        Meal meal = service.get(MEAL_0_ID, USER_ID);
        assertMatch(meal, meal0);
    }

    @Test
    public void getNotFoundByMeal() {
        assertThrows(NotFoundException.class, () -> service.get(MEAL_ID_NOT_EXIST, USER_ID));
    }

    @Test
    public void getNotFoundByUser() {
        assertThrows(NotFoundException.class, () -> service.get(MEAL_1_ID, ADMIN_ID));
    }

    @Test
    public void delete() {
        service.delete(MEAL_0_ID, USER_ID);
        assertThrows(NotFoundException.class, () -> service.get(meal0.getId(), USER_ID));
    }

    @Test
    public void deleteNotFoundByMeal() {
        assertThrows(NotFoundException.class, () -> service.delete(MEAL_ID_NOT_EXIST, USER_ID));
    }

    @Test
    public void deleteNotFoundByUser() {
        assertThrows(NotFoundException.class, () -> service.delete(MEAL_ID_FOR_ADMIN, USER_ID));
    }

    @Test
    public void getBetweenInclusive() {
        List<Meal> selected = service.getBetweenInclusive(LOCAL_DATE_2020_01_30, LOCAL_DATE_2020_01_30, USER_ID);
        assertMatch(selected, betweenInclusiveMeals);
    }

    @Test
    public void getAll() {
        assertMatch(service.getAll(USER_ID), allMeals);
    }

    @Test
    public void update() {
        Meal meal = getUpdated();
        service.update(meal, USER_ID);
        assertMatch(service.get(MEAL_2_ID, USER_ID), getUpdated());
    }

    @Test
    public void updateAlien() {
        assertThrows(NotFoundException.class, () -> service.update(meal2, ADMIN_ID));
    }

    @Test
    public void create() {
        Meal created = service.create(getNew(), USER_ID);
        Integer newId = created.getId();
        Meal newMeal = getNew();
        newMeal.setId(newId);
        assertMatch(created, newMeal);
        assertMatch(service.get(newId, USER_ID), newMeal);
    }

    @Test
    public void createDuplicateDateTime() {
        assertThrows(DuplicateKeyException.class, () -> service.create(mealDuplicateDateTime, USER_ID));
    }

}