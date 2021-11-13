package ru.otus.homework.bytecodes.ioc;

import ru.otus.homework.bytecodes.logging.api.TestLogging;
import ru.otus.homework.bytecodes.logging.impl.TestLoggingImpl;
import ru.otus.homework.bytecodes.util.ReflectionUtils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public final class Ioc {

    private Ioc() {
        throw new UnsupportedOperationException();
    }

    public static TestLogging createTestLoggingInstance() {
        InvocationHandler handler = new LogInvocationHandler(new TestLoggingImpl());
        return (TestLogging) Proxy.newProxyInstance(Ioc.class.getClassLoader(),
                new Class<?>[]{TestLogging.class}, handler);
    }

    private static class LogInvocationHandler implements InvocationHandler {
        private final TestLogging testLogging;
        private final Set<String> logMethodNamesWithParamTypes;

        LogInvocationHandler(TestLogging testLogging) {
            this.testLogging = testLogging;
            logMethodNamesWithParamTypes = ReflectionUtils.getLogMethodNamesWithParamTypes(testLogging);
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args)
                throws InvocationTargetException, IllegalAccessException {
            if (logMethodNamesWithParamTypes.contains(ReflectionUtils.getMethodNameWithParamTypes(method))) {
                logMethodNameAndParams(method, args);
            }
            return method.invoke(testLogging, args);
        }

        private void logMethodNameAndParams(Method method, Object[] args) {
            String params = Arrays.stream(args)
                    .map(Object::toString)
                    .collect(Collectors.joining(", "));
            System.out.printf("executed method: %s with params: %s%n", method.getName(), params);
        }
    }
}
