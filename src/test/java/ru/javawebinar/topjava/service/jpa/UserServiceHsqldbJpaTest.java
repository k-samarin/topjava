package ru.javawebinar.topjava.service.jpa;

import org.springframework.test.annotation.IfProfileValue;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.EnabledIf;
import ru.javawebinar.topjava.ActiveDbProfileResolver;
import ru.javawebinar.topjava.service.UserServiceTest;

@ActiveProfiles(profiles = {"hsqldb", "jpa"}, resolver = ActiveDbProfileResolver.class)
@IfProfileValue(name = "spring.profiles.active", values = {"hsqldb"})
public class UserServiceHsqldbJpaTest extends UserServiceTest {
}