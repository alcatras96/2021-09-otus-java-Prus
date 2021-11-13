package ru.otus.homework.bytecodes.logging.api;

public interface TestLogging {

    void calculation(int param1);

    void calculation(int param1, int param2);

    void calculation(int param1, int param2, String param3);

    void calculation(int param1, int param2, int param3, String param4);
}
