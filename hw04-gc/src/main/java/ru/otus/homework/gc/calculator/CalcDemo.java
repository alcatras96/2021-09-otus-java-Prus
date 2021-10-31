package ru.otus.homework.gc.calculator;


/*
-Xms256m
-Xmx256m
-XX:+HeapDumpOnOutOfMemoryError
-XX:HeapDumpPath=./logs/heapdump.hprof
-XX:+UseG1GC


------------------------------------

Processor Intel Core i7 8700K
32 GB DDR4 RAM 3200MHz

-Xms == -Xmx

------- Without Optimization -------

1. 256m - OutOfMemoryError
2. 266m - spend msec:12826, sec:12
3. 512m - spend msec:10696, sec:10
4. 768m - spend msec:9590, sec:9
5. 1024m - spend msec:9537, sec:9
6. 1280m - spend msec:9521, sec:9
7. 1536m - spend msec:9112, sec:9
8. 2048m - spend msec:9399, sec:9
9. 2304m - spend msec:9240, sec:9

Optimal Heap size - 768m

------------------------------------

------------------------------------
With int and singleton data instance optimization:
1. 256m - spend msec:1431, sec:1
2. 512m - spend msec:1434, sec:1
3. 1024m - spend msec:1424, sec:1
4. 2048 - spend msec:1430, sec:1

------------------------------------
*/


import java.time.LocalDateTime;

public class CalcDemo {
    public static void main(String[] args) {
        long counter = 100_000_000;
        var summator = new Summator();
        long startTime = System.currentTimeMillis();

        var data = new Data();
        for (var idx = 0; idx < counter; idx++) {
            data.setValue(idx);
            summator.calc(data);

            if (idx % 10_000_000 == 0) {
                System.out.println(LocalDateTime.now() + " current idx:" + idx);
            }
        }

        long delta = System.currentTimeMillis() - startTime;
        System.out.println(summator.getPrevValue());
        System.out.println(summator.getPrevPrevValue());
        System.out.println(summator.getSumLastThreeValues());
        System.out.println(summator.getSomeValue());
        System.out.println(summator.getSum());
        System.out.println("spend msec:" + delta + ", sec:" + (delta / 1000));
    }
}
