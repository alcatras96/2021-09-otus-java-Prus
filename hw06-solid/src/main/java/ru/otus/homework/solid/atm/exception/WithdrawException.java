package ru.otus.homework.solid.atm.exception;

public class WithdrawException extends ATMOperationException {

    public WithdrawException(String message) {
        super(message);
    }
}