package ru.javawebinar.topjava.service.datajpa;

import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.ActiveDbProfileResolver;
import ru.javawebinar.topjava.service.UserServiceTest;

@ActiveProfiles(profiles = {"datajpa"}, resolver = ActiveDbProfileResolver.class)
public class DatajpaUserServiceTest extends UserServiceTest {
}
