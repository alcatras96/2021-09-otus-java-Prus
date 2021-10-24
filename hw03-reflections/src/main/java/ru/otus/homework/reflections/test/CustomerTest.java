package ru.otus.homework.reflections.test;


import ru.otus.homework.reflections.annotation.After;
import ru.otus.homework.reflections.annotation.Before;
import ru.otus.homework.reflections.annotation.Test;
import ru.otus.homework.reflections.customer.Customer;
import ru.otus.homework.reflections.customer.CustomerReverseOrder;
import ru.otus.homework.reflections.customer.CustomerService;

import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class CustomerTest {

    private CustomerService customerService;
    private CustomerReverseOrder customerReverseOrder;

    @Before
    void setUp() {
        customerService = new CustomerService();
        customerReverseOrder = new CustomerReverseOrder();
    }

    @After
    void tearDown() {
        customerService.clear();
        customerReverseOrder.clear();
    }

    @Test
    void alwaysFailingTest() {
        assertThat("actual").isEqualTo("expected");
    }

    @Test
    void scoreSortingTest() {
        //given
        Customer customer1 = new Customer(1, "Ivan", 233);
        Customer customer2 = new Customer(2, "Petr", 11);
        Customer customer3 = new Customer(3, "Pavel", 888);

        customerService.add(customer1, "Data1");
        customerService.add(customer2, "Data2");
        customerService.add(customer3, "Data3");

        //when
        Map.Entry<Customer, String> smallestScore = customerService.getSmallest();
        //then
        assertThat(smallestScore.getKey()).isEqualTo(customer2);

        //when
        // подсказка:
        // a key-value mapping associated with the least key strictly greater than the given key, or null if there is no such key.
        Map.Entry<Customer, String> middleScore = customerService.getNext(new Customer(10, "Key", 20));
        //then
        assertThat(middleScore.getKey()).isEqualTo(customer1);
        middleScore.getKey().setScores(10000);
        middleScore.getKey().setName("Vasy");

        //when
        Map.Entry<Customer, String> biggestScore = customerService.getNext(customer1);
        //then
        assertThat(biggestScore.getKey()).isEqualTo(customer3);

        //when
        Map.Entry<Customer, String> notExists = customerService.getNext(new Customer(100, "Not exists", 20000));
        //then
        assertThat(notExists).isNull();
    }

    @Test
    void mutationTest() {
        //given
        Customer customer1 = new Customer(1, "Ivan", 233);
        Customer customer2 = new Customer(2, "Petr", 11);
        Customer customer3 = new Customer(3, "Pavel", 888);

        customerService.add(customer1, "Data1");
        customerService.add(new Customer(customer2.getId(), customer2.getName(), customer2.getScores()), "Data2");
        customerService.add(customer3, "Data3");

        //when
        Map.Entry<Customer, String> smallestScore = customerService.getSmallest();
        smallestScore.getKey().setName("Vasyl");

        //then
        assertThat(customerService.getSmallest().getKey().getName()).isEqualTo(customer2.getName());
    }

    @Test
    void reverseOrderTest() {
        //given
        Customer customer1 = new Customer(1, "Ivan", 233);
        Customer customer2 = new Customer(3, "Petr", 11);
        Customer customer3 = new Customer(2, "Pavel", 888);

        customerReverseOrder.add(customer1);
        customerReverseOrder.add(customer2);
        customerReverseOrder.add(customer3);

        //when
        Customer customerLast = customerReverseOrder.take();
        //then
        assertThat(customerLast).usingRecursiveComparison().isEqualTo(customer3);

        //when
        Customer customerMiddle = customerReverseOrder.take();
        //then
        assertThat(customerMiddle).usingRecursiveComparison().isEqualTo(customer2);

        //when
        Customer customerFirst = customerReverseOrder.take();
        //then
        assertThat(customerFirst).usingRecursiveComparison().isEqualTo(customer1);
    }
}