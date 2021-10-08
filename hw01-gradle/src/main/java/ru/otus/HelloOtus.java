package ru.otus;

import com.google.common.collect.Lists;

import java.util.ArrayList;

public class HelloOtus {
    public static void main(String... args) {
        ArrayList<String> strings = Lists.newArrayList("Hello", "Otus", "!");
        System.out.println(String.join(" ", strings));
    }
}