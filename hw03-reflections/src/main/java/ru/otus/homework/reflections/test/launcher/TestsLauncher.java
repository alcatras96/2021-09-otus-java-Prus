package ru.otus.homework.reflections.test.launcher;

import ru.otus.homework.reflections.annotation.After;
import ru.otus.homework.reflections.annotation.Before;
import ru.otus.homework.reflections.annotation.Test;
import ru.otus.homework.reflections.exception.AnnotationNotFoundException;
import ru.otus.homework.reflections.helper.ReflectionHelper;
import ru.otus.homework.reflections.model.TestResult;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static ru.otus.homework.reflections.helper.ReflectionHelper.getMethodsByAnnotation;

public final class TestsLauncher {

    public List<TestResult> launch(String testClassName) throws ClassNotFoundException {
        Class<?> testClass = Class.forName(testClassName);
        List<Method> testClassMethods = Arrays.asList(testClass.getDeclaredMethods());
        List<Method> testMethods = getMethodsByAnnotation(testClassMethods, Test.class);
        if (testMethods.size() > 0) {
            List<Method> beforeMethods = getMethodsByAnnotation(testClassMethods, Before.class);
            List<Method> afterMethods = getMethodsByAnnotation(testClassMethods, After.class);
            List<TestResult> testResults = new ArrayList<>();

            for (Method testMethod : testMethods) {
                TestResult testResult = runTest(ReflectionHelper.instantiate(testClass), testMethod,
                        beforeMethods, afterMethods);
                testResults.add(testResult);
            }

            return testResults;
        } else {
            throw new AnnotationNotFoundException(String.format("Not found %s annotation on methods in %s test class.",
                    Test.class.getSimpleName(), testClass.getSimpleName()));
        }

    }

    private TestResult runTest(Object testInstance, Method testMethod,
                               List<Method> beforeMethods, List<Method> afterMethods) {
        TestResult testResult = new TestResult(testMethod.getDeclaringClass().getSimpleName(), testMethod.getName());
        beforeMethods.forEach(beforeMethod -> ReflectionHelper.callMethod(testInstance, beforeMethod));
        try {
            ReflectionHelper.callMethod(testInstance, testMethod);
            testResult.setSuccessful(true);
        } catch (RuntimeException exception) {
            testResult.setSuccessful(false);
            testResult.setCause(exception.getCause());
        }
        afterMethods.forEach(afterMethod -> ReflectionHelper.callMethod(testInstance, afterMethod));

        return testResult;
    }
}
