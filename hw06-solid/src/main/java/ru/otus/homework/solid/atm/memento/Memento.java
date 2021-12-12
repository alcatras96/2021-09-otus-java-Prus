package ru.otus.homework.solid.atm.memento;

import ru.otus.homework.solid.atm.vault.api.MoneyVault;

public class Memento {

    private final MoneyVault backup;

    public Memento(MoneyVault vault) {
        this.backup = vault.backup();
    }

    public MoneyVault restore() {
        return backup;
    }
}
