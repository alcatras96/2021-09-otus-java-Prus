package ru.otus.homework.solid.banknote;

import ru.otus.homework.solid.denomination.Denomination;

import java.util.Objects;

public class Banknote implements Comparable<Banknote> {

    private final Denomination denomination;

    private Banknote(Denomination denomination) {
        this.denomination = denomination;
    }

    public Denomination getDenomination() {
        return denomination;
    }

    public static Banknote from(Denomination denomination) {
        return new Banknote(denomination);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Banknote banknote = (Banknote) o;
        return denomination == banknote.denomination;
    }

    @Override
    public int hashCode() {
        return Objects.hash(denomination);
    }

    @Override
    public int compareTo(Banknote banknote) {
        return denomination.compareTo(banknote.getDenomination());
    }

    @Override
    public String toString() {
        return "Banknote{" +
                "denomination=" + denomination +
                '}';
    }
}
