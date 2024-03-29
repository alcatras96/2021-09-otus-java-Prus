package ru.otus.homework;

import java.util.ArrayDeque;
import java.util.Deque;

public class CustomerReverseOrder {

    private final Deque<Customer> customerDeque = new ArrayDeque<>();

    public void add(Customer customer) {
        customerDeque.push(customer);
    }

    public Customer take() {
        return customerDeque.pop();
    }
}
