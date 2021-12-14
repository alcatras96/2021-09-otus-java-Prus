package ru.otus.homework.solid.atm.api;

import ru.otus.homework.solid.atm.exception.DepositException;
import ru.otus.homework.solid.atm.exception.WithdrawException;
import ru.otus.homework.solid.banknote.Banknote;

import java.util.List;

public interface ATM {

    void deposit(List<Banknote> banknotes) throws DepositException;

    List<Banknote> withdraw(int amount) throws WithdrawException;

    int getBalance();
}
