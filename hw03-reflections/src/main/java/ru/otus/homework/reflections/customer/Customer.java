package ru.otus.homework.reflections.customer;

public class Customer implements Comparable<Customer>, Cloneable {
    private final long id;
    private String name;
    private long scores;

    public Customer(long id, String name, long scores) {
        this.id = id;
        this.name = name;
        this.scores = scores;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getScores() {
        return scores;
    }

    public void setScores(long scores) {
        this.scores = scores;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", scores=" + scores +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return id == customer.id;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }


    @Override
    public int compareTo(Customer customer) {
        if (this.getScores() > customer.getScores()) {
            return 1;
        } else if (this.getScores() < customer.getScores()) {
            return -1;
        }
        return 0;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
