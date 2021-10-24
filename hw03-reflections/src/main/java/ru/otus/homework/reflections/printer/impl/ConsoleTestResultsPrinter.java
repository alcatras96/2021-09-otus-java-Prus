package ru.otus.homework.reflections.printer.impl;

import ru.otus.homework.reflections.model.TestResult;
import ru.otus.homework.reflections.printer.api.TestResultsPrinter;

import java.util.List;
import java.util.stream.Collectors;

public class ConsoleTestResultsPrinter implements TestResultsPrinter {

    @Override
    public void print(List<TestResult> testResults) {
        long successfulTests = testResults.stream().filter(TestResult::isSuccessful).count();
        int testsCount = testResults.size();

        System.out.print("=============================================\n"
                + String.format("Tests total: %s. %s successful, %s failed.\n\n",
                testsCount, successfulTests, testsCount - successfulTests)
                + getTestResultsAsString(testResults)
                + "=============================================");
    }

    private String getTestResultsAsString(List<TestResult> testResults) {
        return testResults.stream()
                .map(TestResult::toString)
                .collect(Collectors.joining("\n"));
    }
}
