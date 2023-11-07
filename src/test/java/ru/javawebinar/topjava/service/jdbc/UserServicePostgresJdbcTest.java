package ru.javawebinar.topjava.service.jdbc;

import org.springframework.test.annotation.IfProfileValue;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.ActiveDbProfileResolver;
import ru.javawebinar.topjava.service.UserServiceTest;

@ActiveProfiles(profiles = {"postgres", "jdbc"}, resolver = ActiveDbProfileResolver.class)
@IfProfileValue(name = "spring.profiles.active", values = {"postgres"})
public class UserServicePostgresJdbcTest extends UserServiceTest {
}