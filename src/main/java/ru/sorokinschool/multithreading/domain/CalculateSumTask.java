package ru.sorokinschool.multithreading.domain;

import java.util.List;
import java.util.concurrent.Callable;

public class CalculateSumTask implements Callable<Integer> {

    private final List<Integer> listOfIntegers;
    private final String task;

    public CalculateSumTask(List<Integer> listOfIntegers, String task) {
        this.listOfIntegers = listOfIntegers;
        this.task = task;
    }

    @Override
    public Integer call() throws Exception {
        String threadName = Thread.currentThread().getName();
        System.out.println(task + " - " + threadName);
        Thread.sleep(200);

        return listOfIntegers.stream().mapToInt(Integer::intValue).sum();
    }
}
