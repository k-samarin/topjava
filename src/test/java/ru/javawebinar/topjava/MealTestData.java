package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {
    public static final int MEAL_ID_NOT_EXIST = START_SEQ - 1;
    public static final LocalDate localDate_2020_01_30 = LocalDate.of(2020, Month.JANUARY, 30);
    public static final LocalDate localDate_2020_01_31 = LocalDate.of(2020, Month.JANUARY, 31);
    public static final LocalTime localTime_10_00 = LocalTime.of(10, 0);
    public static final LocalTime localTime_13_00 = LocalTime.of(13, 0);
    public static final LocalTime localTime_20_00 = LocalTime.of(20, 0);
    public static final LocalTime localTime_00_00 = LocalTime.of(00, 0);
    public static final Meal meal0 = new Meal(LocalDateTime.of(localDate_2020_01_30, localTime_10_00), "Завтрак", 500);
    public static final Meal meal1 = new Meal(LocalDateTime.of(localDate_2020_01_30, localTime_13_00), "Обед", 1000);
    public static final Meal meal2 = new Meal(LocalDateTime.of(localDate_2020_01_30, localTime_20_00), "Ужин", 500);
    public static final Meal meal3 = new Meal(LocalDateTime.of(localDate_2020_01_31, localTime_00_00), "Еда на граничное значение", 100);
    public static final Meal meal4 = new Meal(LocalDateTime.of(localDate_2020_01_31, localTime_10_00), "Завтрак", 1000);
    public static final Meal meal5 = new Meal(LocalDateTime.of(localDate_2020_01_31, localTime_13_00), "Обед", 500);
    public static final Meal meal6 = new Meal(LocalDateTime.of(localDate_2020_01_31, localTime_20_00), "Ужин", 410);

    public static final Meal getNew() {
        return new Meal(LocalDateTime.now(), "NewCreatedMeal", 999);
    }

    public static final List<Meal> allMeals = new ArrayList<Meal>() {{
        add(meal6);
        add(meal5);
        add(meal4);
        add(meal3);
        add(meal2);
        add(meal1);
        add(meal0);
    }};

    public static final List<Meal> betweenInclusiveMeals = new ArrayList<Meal>() {{
        add(meal2);
        add(meal1);
        add(meal0);
    }};

    public static void AssertMatch(Meal actual, Meal expected) {
        assertThat(actual).usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(expected);
    }
}
