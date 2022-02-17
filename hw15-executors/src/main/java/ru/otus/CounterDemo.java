package ru.otus;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static ru.otus.ThreadPriority.FIRST;
import static ru.otus.ThreadPriority.SECOND;

public class CounterDemo {
    private static final Logger logger = LoggerFactory.getLogger(CounterDemo.class);

    private ThreadPriority latestRegisteredPriority = SECOND;

    public static void main(String[] args) {
        CounterDemo counterDemo = new CounterDemo();
        new Thread(() -> counterDemo.action(FIRST)).start();
        new Thread(() -> counterDemo.action(SECOND)).start();
    }

    private synchronized void action(ThreadPriority priority) {
        try {
            while (priority.equals(latestRegisteredPriority)) {
                wait();
            }
            latestRegisteredPriority = priority;

            Counter counter = new Counter();
            while (!Thread.currentThread().isInterrupted()) {
                logger.info(String.valueOf(counter.calculateAndGet()));
                sleep();
                notifyAll();
                wait();
            }
        } catch (InterruptedException exception) {
            Thread.currentThread().interrupt();
        }
    }

    private static void sleep() {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            logger.error(e.getMessage());
            Thread.currentThread().interrupt();
        }
    }
}
