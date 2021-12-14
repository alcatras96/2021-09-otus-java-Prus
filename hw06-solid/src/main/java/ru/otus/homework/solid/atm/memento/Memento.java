package ru.otus.homework.solid.atm.memento;

import ru.otus.homework.solid.denomination.Denomination;

import java.util.SortedMap;
import java.util.TreeMap;

public class Memento {

    private final SortedMap<Denomination, Integer> backup;

    public Memento(SortedMap<Denomination, Integer> cells) {
        this.backup = new TreeMap<>(cells);
    }

    public SortedMap<Denomination, Integer> restore() {
        return backup;
    }
}
