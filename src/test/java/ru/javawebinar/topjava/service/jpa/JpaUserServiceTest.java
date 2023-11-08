package ru.javawebinar.topjava.service.jpa;

import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.ActiveDbProfileResolver;
import ru.javawebinar.topjava.service.UserServiceTest;

@ActiveProfiles(profiles = {"jpa"}, resolver = ActiveDbProfileResolver.class)
public class JpaUserServiceTest extends UserServiceTest {
}
