package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {
    public static final int MEAL_ID_NOT_EXIST = START_SEQ - 1;
    public static final int MEAL_ID_START_INDEX = START_SEQ + 1;
    public static final int MEAL_ID_FOR_ADMIN = MEAL_ID_START_INDEX + 2;
    public static final int MEAL_0_ID = MEAL_ID_FOR_ADMIN + 1;
    public static final int MEAL_1_ID = MEAL_0_ID + 1;
    public static final int MEAL_2_ID = MEAL_1_ID + 1;
    public static final int MEAL_3_ID = MEAL_2_ID + 1;
    public static final int MEAL_4_ID = MEAL_3_ID + 1;
    public static final int MEAL_5_ID = MEAL_4_ID + 1;
    public static final int MEAL_6_ID = MEAL_5_ID + 1;
    public static final LocalDate localDate_2020_01_30 = LocalDate.of(2020, Month.JANUARY, 30);
    public static final LocalDate localDate_2020_01_31 = LocalDate.of(2020, Month.JANUARY, 31);
    public static final LocalTime localTime_10_00 = LocalTime.of(10, 0);
    public static final LocalTime localTime_13_00 = LocalTime.of(13, 0);
    public static final LocalTime localTime_20_00 = LocalTime.of(20, 0);
    public static final LocalTime localTime_00_00 = LocalTime.of(0, 0);

    public static final LocalDateTime localDateTime_new = LocalDateTime.of(2020, Month.FEBRUARY, 01, 10, 0);

    public static final Meal meal0 = new Meal(MEAL_0_ID, LocalDateTime.of(localDate_2020_01_30, localTime_10_00), "Завтрак", 500);
    public static final Meal meal1 = new Meal(MEAL_1_ID, LocalDateTime.of(localDate_2020_01_30, localTime_13_00), "Обед", 1000);
    public static final Meal meal2 = new Meal(MEAL_2_ID, LocalDateTime.of(localDate_2020_01_30, localTime_20_00), "Ужин", 500);
    public static final Meal meal3 = new Meal(MEAL_3_ID, LocalDateTime.of(localDate_2020_01_31, localTime_00_00), "Еда на граничное значение", 100);
    public static final Meal meal4 = new Meal(MEAL_4_ID, LocalDateTime.of(localDate_2020_01_31, localTime_10_00), "Завтрак", 1000);
    public static final Meal meal5 = new Meal(MEAL_5_ID, LocalDateTime.of(localDate_2020_01_31, localTime_13_00), "Обед", 500);
    public static final Meal meal6 = new Meal(MEAL_6_ID, LocalDateTime.of(localDate_2020_01_31, localTime_20_00), "Ужин", 410);
    public static final Meal mealDuplicateDateTime = new Meal(LocalDateTime.of(localDate_2020_01_31, localTime_20_00), "Ужин duplicate datetime", 410);

    public static final Meal meal2Update = new Meal(MEAL_2_ID, localDateTime_new, "NewDescription", 999);

    public static final List<Meal> allMeals = Arrays.asList(
            meal6,
            meal5,
            meal4,
            meal3,
            meal2,
            meal1,
            meal0
    );

    public static final List<Meal> betweenInclusiveMeals = Arrays.asList(
            meal2,
            meal1,
            meal0
    );


    public static Meal getNew() {
        return new Meal(localDateTime_new, "NewCreatedMeal", 999);
    }

    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    public static void assertMatch(List<Meal> actual, List<Meal> expected){
        assertThat(actual).usingRecursiveFieldByFieldElementComparator().isEqualTo(expected);
    }
}
