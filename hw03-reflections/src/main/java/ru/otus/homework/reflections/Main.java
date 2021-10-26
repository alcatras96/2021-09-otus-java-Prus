package ru.otus.homework.reflections;

import ru.otus.homework.reflections.model.TestResult;
import ru.otus.homework.reflections.printer.impl.ConsoleTestResultsPrinter;
import ru.otus.homework.reflections.test.launcher.TestsLauncher;

import java.util.List;

public class Main {

    public static void main(String[] args) throws ClassNotFoundException {
        TestsLauncher testsLauncher = new TestsLauncher();
        List<TestResult> results = testsLauncher.launch("ru.otus.homework.reflections.test.CustomerTest");
        new ConsoleTestResultsPrinter().print(results);
    }
}
