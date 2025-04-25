package ru.sorokinschool.multithreading.util;

import ru.sorokinschool.multithreading.domain.CalculateSumTask;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class DataProcessor {

    private final Map<String, Integer> repository = new HashMap<>();
    private final ExecutorService executor;
    private final AtomicInteger taskCounter = new AtomicInteger(0);
    private final AtomicInteger counterOfActiveTasks = new AtomicInteger(0);
    private final Object lock = new Object();

    public DataProcessor(int numberOfThreads) {
        this.executor = Executors.newFixedThreadPool(numberOfThreads);
    }

    public void init(List<Integer> numbers, String taskName) {
        String task = "task" + taskCounter.incrementAndGet();
        CalculateSumTask calculateSumTask = new CalculateSumTask(numbers, task);
        counterOfActiveTasks.incrementAndGet();

        executor.submit(() -> {
            try {
                Integer result = calculateSumTask.call();
                synchronized (lock) {
                    repository.put(taskName, result);
                }
                return result;
            } finally {
                counterOfActiveTasks.decrementAndGet();
            }
        });
    }

    public int getActiveTaskCount() {
        return counterOfActiveTasks.get();
    }

    public Optional<Integer> getTaskResult(String taskName) {
        synchronized (lock) {
            return Optional.ofNullable(repository.get(taskName));
        }
    }

    public Map<String, Integer> getRepository() {
        return repository;
    }

    public void shutdown() {
        executor.shutdown();
    }
}
