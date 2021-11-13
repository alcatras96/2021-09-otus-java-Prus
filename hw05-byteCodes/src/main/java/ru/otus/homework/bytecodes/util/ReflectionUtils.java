package ru.otus.homework.bytecodes.util;

import ru.otus.homework.bytecodes.annotation.Log;
import ru.otus.homework.bytecodes.logging.api.TestLogging;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Set;
import java.util.StringJoiner;
import java.util.stream.Collectors;

public final class ReflectionUtils {

    private ReflectionUtils() {
        throw new UnsupportedOperationException();
    }

    public static Set<String> getLogMethodNamesWithParams(TestLogging testLogging) {
        return Arrays.stream(testLogging.getClass().getDeclaredMethods())
                .filter(method -> method.isAnnotationPresent(Log.class))
                .map(ReflectionUtils::getMethodNameWithParams)
                .collect(Collectors.toSet());
    }

    public static String getMethodNameWithParams(Method method) {
        StringJoiner joiner = new StringJoiner(",", method.getName() + "{", "}");
        Arrays.stream(method.getParameterTypes())
                .forEach(methodTypeVariable -> joiner.add(methodTypeVariable.getTypeName()));

        return joiner.toString();
    }
}
