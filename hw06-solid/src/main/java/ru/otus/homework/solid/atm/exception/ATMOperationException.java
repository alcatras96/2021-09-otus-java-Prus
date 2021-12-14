package ru.otus.homework.solid.atm.exception;

public abstract class ATMOperationException extends RuntimeException {

    public ATMOperationException(String message) {
        super(message);
    }
}
