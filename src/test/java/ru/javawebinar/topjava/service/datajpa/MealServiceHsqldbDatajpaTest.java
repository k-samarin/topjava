package ru.javawebinar.topjava.service.datajpa;

import org.springframework.test.annotation.IfProfileValue;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.EnabledIf;
import ru.javawebinar.topjava.ActiveDbProfileResolver;
import ru.javawebinar.topjava.service.MealServiceTest;

@ActiveProfiles(profiles = {"hsqldb", "datajpa"}, resolver = ActiveDbProfileResolver.class)
@IfProfileValue(name = "spring.profiles.active", values = {"hsqldb"})
public class MealServiceHsqldbDatajpaTest extends MealServiceTest {
}