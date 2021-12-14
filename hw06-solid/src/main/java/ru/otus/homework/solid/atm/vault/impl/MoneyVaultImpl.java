package ru.otus.homework.solid.atm.vault.impl;

import ru.otus.homework.solid.atm.memento.Memento;
import ru.otus.homework.solid.atm.vault.api.MoneyVault;
import ru.otus.homework.solid.banknote.Banknote;
import ru.otus.homework.solid.denomination.Denomination;

import java.util.*;

public class MoneyVaultImpl implements MoneyVault {

    private SortedMap<Denomination, Integer> cells;

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

        Memento memento = new Memento(cells);
        amount = calculateBanknotesToReceive(banknotesToReceive, amount);

        if (amount != 0) {
            cells = memento.restore();
            banknotesToReceive = Collections.emptyList();
        }

        return banknotesToReceive;
    }

    @Override
    public int getTotalAmount() {
        return cells.entrySet()
                .stream()
                .mapToInt(this::getBanknotesAmount)
                .sum();
    }

    private int calculateBanknotesToReceive(List<Banknote> banknotesToReceive, int amount) {
        for (Map.Entry<Denomination, Integer> denominationIntegerEntry : cells.entrySet()) {
            if (amount == 0) {
                break;
            }

            Integer banknotesCountInVault = denominationIntegerEntry.getValue();
            if (banknotesCountInVault != null && banknotesCountInVault > 0) {
                Denomination denomination = denominationIntegerEntry.getKey();
                int denominationValue = denomination.getValue();
                int banknotesCountInAmount = amount / denominationValue;
                if (banknotesCountInAmount > 0) {
                    int banknotesCountToReceive = banknotesCountInVault < banknotesCountInAmount
                            ? banknotesCountInVault : banknotesCountInAmount;
                    banknotesToReceive.addAll(Collections.nCopies(banknotesCountToReceive, Banknote.from(denomination)));
                    denominationIntegerEntry.setValue(banknotesCountInVault - banknotesCountToReceive);
                    amount -= banknotesCountToReceive * denominationValue;
                }
            }
        }

        return amount;
    }

    private int getBanknotesAmount(Map.Entry<Denomination, Integer> banknoteIntegerEntry) {
        return banknoteIntegerEntry.getKey().getValue() * banknoteIntegerEntry.getValue();
    }

    private void arrangeBanknotesToCells(List<Banknote> banknotes) {
        for (Banknote banknote : banknotes) {
            Denomination denomination = banknote.getDenomination();
            Integer banknotesCount = cells.get(denomination);
            cells.put(denomination, banknotesCount == null ? 1 : ++banknotesCount);
        }
    }
}
