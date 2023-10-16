package ru.javawebinar.topjava;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.web.meal.MealRestController;
import ru.javawebinar.topjava.web.user.AdminRestController;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

public class SpringMain {
    public static final List<Meal> mealsFebruaryUser2 = Arrays.asList(
            new Meal(LocalDateTime.of(2020, Month.FEBRUARY, 10, 10, 0), "Завтрак", 500, 2),
            new Meal(LocalDateTime.of(2020, Month.FEBRUARY, 10, 10, 12), "Кофе", 120, 2)
    );

    public static final List<Meal> mealsJenuaryUser1 = Arrays.asList(
            new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500, 1),
            new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000, 1),
            new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500, 1),
            new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100, 1),
            new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000, 1),
            new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500, 1),
            new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410, 1));

    public static final List<Meal> mealsJenuaryUser2 = Arrays.asList(
            new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500, 2),
            new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100, 2)
    );

    public static void main(String[] args) {

        // java 7 automatic resource management (ARM)
        try (ConfigurableApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml")) {
            System.out.println("Bean definition names: " + Arrays.toString(appCtx.getBeanDefinitionNames()));
            AdminRestController adminUserController = appCtx.getBean(AdminRestController.class);
            adminUserController.create(new User(null, "userName", "email@mail.ru", "password", Role.ADMIN));

            MealRestController mealRestController = appCtx.getBean(MealRestController.class);
            mealRestController.create(
                    new Meal(LocalDateTime.of(2020, Month.MARCH, 10, 10, 0), "March Завтрак", 500, 2)
            );

            mealsFebruaryUser2.forEach(meal -> mealRestController.create(meal));
            mealsJenuaryUser1.forEach(meal -> mealRestController.create(meal));
            mealsJenuaryUser2.forEach(meal -> mealRestController.create(meal));

            try {
                mealRestController.update(
                        new Meal(12, LocalDateTime.of(2020, Month.MARCH, 10, 10, 0), "March Завтрак", 500, 1), 11
                );
            } catch (Exception e) {
                System.out.println("Exception " + e.getMessage());
            }

            mealRestController.update(
                    new Meal(6, LocalDateTime.of(2020, Month.MARCH, 10, 10, 0), "March Завтрак updated", 500, 1), 6
            );

            mealRestController.create(
                    new Meal(LocalDateTime.of(2020, Month.MARCH, 10, 10, 0), "March Завтрак", 500, 1)
            );

            mealRestController.getAll().forEach(System.out::println);

            mealRestController.delete(5);

            mealRestController.getAll().forEach(System.out::println);

            LocalDateTime startDateTime = LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0);
            LocalDateTime endDateTime = LocalDateTime.of(2020, Month.JANUARY, 31, 13, 10);
            mealRestController.getAllFiltered(
                            startDateTime.toLocalDate(),
                            startDateTime.toLocalTime(),
                            endDateTime.toLocalDate(),
                            endDateTime.toLocalTime())
                    .forEach(System.out::println);
        }
    }
}
