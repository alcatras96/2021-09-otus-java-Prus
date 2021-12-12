package ru.otus.homework.solid.atm.impl;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.homework.solid.atm.api.ATM;
import ru.otus.homework.solid.atm.exception.WithdrawException;
import ru.otus.homework.solid.atm.vault.impl.MoneyVaultImpl;
import ru.otus.homework.solid.banknote.Banknote;
import ru.otus.homework.solid.denomination.Denomination;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static ru.otus.homework.solid.denomination.Denomination.*;

public class ATMImplTest {

    private ATM atm;

    @BeforeEach
    void setUp() {
        atm = new ATMImpl(new MoneyVaultImpl());
        List<Banknote> initialBanknotesInAtm = Arrays.asList(
                Banknote.from(Denomination.THOUSAND),
                Banknote.from(Denomination.FIVE),
                Banknote.from(Denomination.TEN),
                Banknote.from(Denomination.FIVE_HUNDRED),
                Banknote.from(HUNDRED),
                Banknote.from(Denomination.FIVE),
                Banknote.from(Denomination.FIVE),
                Banknote.from(Denomination.THOUSAND)
        );
        atm.deposit(initialBanknotesInAtm);
    }

    @Test
    @DisplayName("Should throw an exception if user try to withdraw negative amount of money")
    void shouldThrowExceptionIfWithdrawNegativeAmountOfMoney() {
        assertThrows(WithdrawException.class, () -> atm.withdraw(-1));
    }

    @Test
    @DisplayName("Should throw an exception if user try to withdraw zero amount of money")
    void shouldThrowExceptionIfWithdrawZeroAmountOfMoney() {
        assertThrows(WithdrawException.class, () -> atm.withdraw(0));
    }

    @Test
    @DisplayName("Should throw an exception if user try to withdraw amount of money bigger than the amount in ATM")
    void shouldThrowExceptionIfWithdrawBiggerAmountOfMoneyThanInATM() {
        assertThrows(WithdrawException.class, () -> atm.withdraw(Integer.MAX_VALUE));
    }

    @Test
    @DisplayName("Should deposit specified amount of money from ATM")
    void shouldDepositSpecifiedAmountOfMoney() {
        int balanceBeforeDeposit = atm.getBalance();

        List<Banknote> banknotesToDeposit = Arrays.asList(
                Banknote.from(HUNDRED),
                Banknote.from(THOUSAND),
                Banknote.from(FIVE_HUNDRED)
        );

        atm.deposit(banknotesToDeposit);

        assertThat(atm.getBalance()).isEqualTo(balanceBeforeDeposit + getAmount(banknotesToDeposit));
    }

    @Test
    @DisplayName("Should withdraw specified amount of money")
    void shouldWithdrawSpecifiedAmountOfMoney() {
        int balanceBeforeWithdraw = atm.getBalance();

        int amountTOWithdraw = 1600;
        atm.withdraw(amountTOWithdraw);

        assertThat(balanceBeforeWithdraw).isEqualTo(atm.getBalance() + amountTOWithdraw);
    }

    @Test
    @DisplayName("Should restore ATM state in case of insufficient amount of money")
    void shouldRestoreATMStateInCaseOfInsufficientAmountOfMoney() {
        int balanceBeforeWithdraw = atm.getBalance();

        assertThrows(WithdrawException.class, () -> atm.withdraw(1002));

        assertThat(balanceBeforeWithdraw).isEqualTo(atm.getBalance());
    }

    @Test
    @DisplayName("Should give the requested amount with the minimum number of banknotes")
    void shouldGiveTheRequestedAmountWithTheMinimumNumberOfBanknotes() {
        List<Banknote> banknotesToCompare = Arrays.asList(Banknote.from(THOUSAND), Banknote.from(FIVE_HUNDRED), Banknote.from(TEN));

        List<Banknote> banknotes = atm.withdraw(getAmount(banknotesToCompare));

        assertThat(banknotes).isEqualTo(banknotesToCompare);
    }

    private int getAmount(List<Banknote> banknotes) {
        return banknotes.stream().map(Banknote::getDenomination).mapToInt(Denomination::getValue).sum();
    }
}