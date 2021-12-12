package ru.otus.homework.solid.atm.vault.api;

import ru.otus.homework.solid.banknote.Banknote;

import java.util.List;

public interface MoneyVault {

    void put(List<Banknote> banknotes);

    List<Banknote> get(int amount);

    int getTotalAmount();

    MoneyVault backup();
}
