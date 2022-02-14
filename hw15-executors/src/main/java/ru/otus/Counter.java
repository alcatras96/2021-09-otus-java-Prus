package ru.otus;

public class Counter {

    private int value = 0;
    private boolean reverseDirection = false;

    public int calculateAndGet() {
        calculateDirection();
        return reverseDirection ? --value : ++value;
    }

    private void calculateDirection() {
        if (value == 10) {
            reverseDirection = true;
        } else if (value == 1) {
            reverseDirection = false;
        }
    }
}
