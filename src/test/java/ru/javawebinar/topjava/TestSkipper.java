package ru.javawebinar.topjava;

import org.junit.Assume;
import org.junit.rules.MethodRule;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.Statement;

public class TestSkipper implements MethodRule {

    private final String testName;

    public TestSkipper(String testName) {
        this.testName = testName;
    }

    @Override
    public Statement apply(Statement base, FrameworkMethod method, Object target) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                Assume.assumeFalse(testName.equalsIgnoreCase(method.getName()));
                base.evaluate();
            }
        };
    }
}
