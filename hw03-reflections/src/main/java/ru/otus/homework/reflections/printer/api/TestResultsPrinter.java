package ru.otus.homework.reflections.printer.api;

import ru.otus.homework.reflections.model.TestResult;

import java.util.List;

public interface TestResultsPrinter {

    void print(List<TestResult> testResults);
}
