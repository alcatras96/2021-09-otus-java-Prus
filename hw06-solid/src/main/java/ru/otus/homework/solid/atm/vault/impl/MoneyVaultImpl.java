package ru.otus.homework.solid.atm.vault.impl;

import ru.otus.homework.solid.atm.vault.api.MoneyVault;
import ru.otus.homework.solid.banknote.Banknote;

import java.util.*;

public class MoneyVaultImpl implements MoneyVault {

    private final SortedMap<Banknote, Integer> cells;

    public MoneyVaultImpl() {
        cells = new TreeMap<>(Collections.reverseOrder());
    }

    @Override
    public void put(List<Banknote> banknotes) {
        arrangeBanknotesToCells(banknotes);
    }

    @Override
    public List<Banknote> get(int amount) {
        if (getTotalAmount() < amount) {
            return Collections.emptyList();
        }

        List<Banknote> banknotesToReceive = new ArrayList<>();

        amount = calculateBanknotesToReceive(banknotesToReceive, amount);

        return amount == 0 ? banknotesToReceive : Collections.emptyList();
    }

    @Override
    public int getTotalAmount() {
        return cells.entrySet()
                .stream()
                .mapToInt(this::getBanknotesAmount)
                .sum();
    }

    private int calculateBanknotesToReceive(List<Banknote> banknotesToReceive, int amount) {
        for (Map.Entry<Banknote, Integer> banknoteIntegerEntry : cells.entrySet()) {
            if (amount == 0) {
                break;
            }

            Integer banknotesCountInVault = banknoteIntegerEntry.getValue();
            if (banknotesCountInVault != null && banknotesCountInVault > 0) {
                Banknote banknote = banknoteIntegerEntry.getKey();
                int denomination = banknote.getDenomination().getValue();
                int banknotesCountInAmount = amount / denomination;
                if (banknotesCountInAmount > 0) {
                    int banknotesCountToReceive = banknotesCountInVault < banknotesCountInAmount
                            ? banknotesCountInVault : banknotesCountInAmount;
                    banknotesToReceive.addAll(Collections.nCopies(banknotesCountToReceive, banknote));
                    banknoteIntegerEntry.setValue(banknotesCountInVault - banknotesCountToReceive);
                    amount -= banknotesCountToReceive * denomination;
                }
            }
        }

        return amount;
    }

    private int getBanknotesAmount(Map.Entry<Banknote, Integer> banknoteIntegerEntry) {
        return banknoteIntegerEntry.getKey().getDenomination().getValue() * banknoteIntegerEntry.getValue();
    }

    private void arrangeBanknotesToCells(List<Banknote> banknotes) {
        Objects.requireNonNull(banknotes);

        for (Banknote banknote : banknotes) {
            Integer banknotesCount = cells.get(banknote);
            cells.put(banknote, banknotesCount == null ? 1 : ++banknotesCount);
        }
    }
}
