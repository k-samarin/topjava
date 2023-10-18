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

public class SpringMain {

    public static void main(String[] args) {

        // java 7 automatic resource management (ARM)
        try (ConfigurableApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml")) {
            System.out.println("Bean definition names: " + Arrays.toString(appCtx.getBeanDefinitionNames()));
            AdminRestController adminUserController = appCtx.getBean(AdminRestController.class);
            adminUserController.create(new User(null, "userName", "email@mail.ru", "password", Role.ADMIN));

            MealRestController mealRestController = appCtx.getBean(MealRestController.class);
            mealRestController.create(
                    new Meal(LocalDateTime.of(2020, Month.MARCH, 10, 10, 0), "March Завтрак", 500)
            );

            try {
                mealRestController.update(
                        new Meal(12, LocalDateTime.of(2020, Month.MARCH, 10, 10, 0), "March Завтрак", 500), 11
                );
            } catch (Exception e) {
                System.out.println("Exception " + e.getMessage());
            }

            mealRestController.update(
                    new Meal(6, LocalDateTime.of(2020, Month.MARCH, 10, 10, 0), "March Завтрак updated", 500), 6
            );

            mealRestController.create(
                    new Meal(LocalDateTime.of(2020, Month.MARCH, 10, 10, 0), "March Завтрак", 500)
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
