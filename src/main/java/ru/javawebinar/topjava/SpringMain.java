package ru.javawebinar.topjava;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.util.exception.NotFoundException;
import ru.javawebinar.topjava.web.meal.MealRestController;
import ru.javawebinar.topjava.web.user.AdminRestController;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.Collection;

public class SpringMain {
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

            try {
                mealRestController.update(
                        new Meal(11, LocalDateTime.of(2020, Month.MARCH, 10, 10, 0), "March Завтрак", 500, 1)
                );
            } catch (NotFoundException e) {
                System.out.println(e.getMessage());
            }

            mealRestController.create(
                    new Meal(LocalDateTime.of(2020, Month.MARCH, 10, 10, 0), "March Завтрак", 500, 1)
            );

            mealRestController.getAll().forEach(System.out::println);

            mealRestController.delete(5);

            mealRestController.getAll().forEach(System.out::println);
        }
    }
}
