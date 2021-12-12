package ru.otus.homework.solid.atm.api;

import ru.otus.homework.solid.banknote.Banknote;

import java.util.List;

public interface ATM {

    void deposit(List<Banknote> banknotes);

    List<Banknote> withdraw(int amount);

    int getBalance();
}
