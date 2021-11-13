package ru.otus.homework.bytecodes.logging.impl;

import ru.otus.homework.bytecodes.annotation.Log;
import ru.otus.homework.bytecodes.logging.api.TestLogging;

public class TestLoggingImpl implements TestLogging {

    @Log
    @Override
    public void calculation() {
    }

    @Log
    @Override
    public void calculation(int param1) {
    }

    @Log
    @Override
    public void calculation(int param1, int param2) {
    }

    @Log
    @Override
    public void calculation(int param1, int param2, String param3) {
    }

    @Override
    public void calculation(int param1, int param2, int param3, String param4) {
    }
}
