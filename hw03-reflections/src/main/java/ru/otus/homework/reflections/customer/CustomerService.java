package ru.otus.homework.reflections.customer;

import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;

public class CustomerService {

    private final NavigableMap<Customer, String> customersMap = new TreeMap<>();

    public Map.Entry<Customer, String> getSmallest() {
        return copyOf(customersMap.firstEntry());
    }

    public Map.Entry<Customer, String> getNext(Customer customer) {
        return copyOf(customersMap.higherEntry(customer));
    }

    public void add(Customer customer, String data) {
        try {
            if (customer != null) {
                Customer clonedCustomer = (Customer) customer.clone();
                customersMap.put(clonedCustomer, data);
            }
        } catch (CloneNotSupportedException ex) {
            ex.printStackTrace();
        }
    }

    public void clear() {
        customersMap.clear();
    }

    private Map.Entry<Customer, String> copyOf(Map.Entry<Customer, String> entry) {
        if (entry == null) {
            return null;
        }
        try {
            return Map.entry((Customer) entry.getKey().clone(), entry.getValue());
        } catch (CloneNotSupportedException ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
