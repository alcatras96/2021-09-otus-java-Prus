package ru.otus.homework.solid;

import ru.otus.homework.solid.atm.api.ATM;
import ru.otus.homework.solid.atm.impl.ATMImpl;
import ru.otus.homework.solid.atm.vault.impl.MoneyVaultImpl;
import ru.otus.homework.solid.banknote.Banknote;
import ru.otus.homework.solid.denomination.Denomination;

import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
        ATM atm = new ATMImpl(new MoneyVaultImpl());

        atm.deposit(Arrays.asList(
                Banknote.from(Denomination.THOUSAND),
                Banknote.from(Denomination.FIVE),
                Banknote.from(Denomination.TEN),
                Banknote.from(Denomination.FIVE_HUNDRED),
                Banknote.from(Denomination.FIVE),
                Banknote.from(Denomination.FIVE),
                Banknote.from(Denomination.THOUSAND))
        );

        System.out.println("Current ATM balance is " + atm.getBalance());

        System.out.println("Withdraw 25 currency from ATM - " + atm.withdraw(25));
    }
}
