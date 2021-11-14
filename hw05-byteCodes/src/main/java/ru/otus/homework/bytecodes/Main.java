package ru.otus.homework.bytecodes;

import ru.otus.homework.bytecodes.ioc.Ioc;
import ru.otus.homework.bytecodes.logging.api.TestLogging;
import ru.otus.homework.bytecodes.logging.impl.TestLoggingImpl;

public class Main {

    public static void main(String[] args) {
        TestLogging testLogging = Ioc.createTestLoggingInstance(new TestLoggingImpl());

        testLogging.calculation();
        testLogging.calculation(1);
        testLogging.calculation(1, 2);
        testLogging.calculation(1, 2, "param");

        testLogging.calculation(1, 2, 3, "param");
    }
}
