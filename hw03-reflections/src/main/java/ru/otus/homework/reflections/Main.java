package ru.otus.homework.reflections;

import ru.otus.homework.reflections.printer.impl.ConsoleTestResultsPrinter;
import ru.otus.homework.reflections.test.launcher.TestsLauncher;

public class Main {

    public static void main(String[] args) throws ClassNotFoundException {
        TestsLauncher testsLauncher = new TestsLauncher(new ConsoleTestResultsPrinter());
        testsLauncher.launch("ru.otus.homework.reflections.test.CustomerTest");
    }
}
