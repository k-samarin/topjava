package ru.javawebinar.topjava.service.datajpa;

import org.springframework.test.annotation.IfProfileValue;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.ActiveDbProfileResolver;
import ru.javawebinar.topjava.service.MealServiceTest;

@ActiveProfiles(profiles = {"postgres", "datajpa"}, resolver = ActiveDbProfileResolver.class)
@IfProfileValue(name = "spring.profiles.active", values = {"postgres"})
public class MealServicePostgresDatajpaTest extends MealServiceTest {
}