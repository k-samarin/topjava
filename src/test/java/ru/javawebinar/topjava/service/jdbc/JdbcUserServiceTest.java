package ru.javawebinar.topjava.service.jdbc;

import org.junit.Rule;
import org.junit.rules.MethodRule;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.TestSkipper;
import ru.javawebinar.topjava.service.AbstractUserServiceNoCacheTest;

import static org.springframework.test.annotation.DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD;
import static ru.javawebinar.topjava.Profiles.JDBC;

@DirtiesContext(classMode = BEFORE_EACH_TEST_METHOD)
@ActiveProfiles(JDBC)
public class JdbcUserServiceTest extends AbstractUserServiceNoCacheTest {
    @Rule
    public MethodRule testSkipper = new TestSkipper("createWithException");
}