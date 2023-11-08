package ru.javawebinar.topjava.service.jdbc;

import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.ActiveDbProfileResolver;
import ru.javawebinar.topjava.service.MealServiceTest;

@ActiveProfiles(profiles = {"jdbc"}, resolver = ActiveDbProfileResolver.class)
public class JdbcMealServiceTest extends MealServiceTest {
}
