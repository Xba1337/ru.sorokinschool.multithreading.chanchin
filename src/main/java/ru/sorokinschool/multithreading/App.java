package ru.sorokinschool.multithreading;

import ru.sorokinschool.multithreading.util.DataProcessor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class App {

    public static void main(String[] args) {
        DataProcessor dataProcessor = new DataProcessor(4);

        for (int i = 1; i <= 100; i++) {
            List<Integer> numbers = new ArrayList<>();
            for (int j = i; j < i * 10; j++) {
                numbers.add(j);
            }
            String taskName = "task" + i;
            dataProcessor.init(numbers, taskName);
        }

        while (dataProcessor.getActiveTaskCount() > 0) {
            System.out.println("Количество активных задач: " + dataProcessor.getActiveTaskCount());
            try {
                Thread.sleep(250);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        if (dataProcessor.getActiveTaskCount() == 0) {
            System.out.println("Работа выполнена!");
        }

        for (Map.Entry<String, Integer> entry : dataProcessor.getRepository().entrySet()) {
            System.out.println(dataProcessor.getTaskResult(entry.getKey()));
        }
        dataProcessor.shutdown();
    }
}
