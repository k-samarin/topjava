package ru.javawebinar.topjava.service.jdbc;

import org.junit.Rule;
import org.junit.rules.MethodRule;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.TestSkipper;
import ru.javawebinar.topjava.service.AbstractMealServiceTest;

import static ru.javawebinar.topjava.Profiles.JDBC;

@ActiveProfiles(JDBC)
public class JdbcMealServiceTest extends AbstractMealServiceTest {
    @Rule
    public MethodRule testSkipper = new TestSkipper("createWithException");
}