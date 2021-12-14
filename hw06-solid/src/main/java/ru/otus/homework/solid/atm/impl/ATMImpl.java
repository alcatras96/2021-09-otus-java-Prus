package ru.otus.homework.solid.atm.impl;

import ru.otus.homework.solid.atm.api.ATM;
import ru.otus.homework.solid.atm.exception.DepositException;
import ru.otus.homework.solid.atm.exception.WithdrawException;
import ru.otus.homework.solid.atm.vault.api.MoneyVault;
import ru.otus.homework.solid.banknote.Banknote;

import java.util.List;

public class ATMImpl implements ATM {

    private final MoneyVault vault;

    public ATMImpl(MoneyVault vault) {
        this.vault = vault;
    }

    @Override
    public void deposit(List<Banknote> banknotes) throws DepositException {
        if (banknotes == null) {
            throw new DepositException("Banknotes list cannot be null.");
        }

        vault.put(banknotes);
    }

    @Override
    public List<Banknote> withdraw(int amount) throws WithdrawException {
        if (amount <= 0) {
            throw new WithdrawException("Cannot withdraw zero or negative amount.");
        }

        List<Banknote> banknotes = vault.get(amount);

        if (banknotes.isEmpty()) {
            throw new WithdrawException(String.format("Cannot withdraw %s amount from vault", amount));
        }

        return banknotes;
    }

    @Override
    public int getBalance() {
        return vault.getTotalAmount();
    }
}
