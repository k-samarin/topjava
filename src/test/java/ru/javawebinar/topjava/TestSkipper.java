package ru.javawebinar.topjava;

import org.junit.AssumptionViolatedException;
import org.junit.Rule;
import org.junit.rules.MethodRule;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.Statement;

public class TestSkipper implements MethodRule {

    private String testName;

    public TestSkipper(String testName) {
        this.testName = testName;
    }

    @Override
    public Statement apply(Statement base, FrameworkMethod method, Object target) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                if (testName.equalsIgnoreCase(method.getName())) {
                    throw new AssumptionViolatedException(
                            String.format("Test %s#%s is skipped", target.getClass().getName(), method.getName()));
                }
                base.evaluate();
            }
        };
    }
}
