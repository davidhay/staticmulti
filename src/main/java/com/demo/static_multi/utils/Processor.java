package com.demo.static_multi.utils;

import lombok.SneakyThrows;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

public class Processor {

    public List<Integer> process1(List<Integer> values) {
        return values.stream().map(SizeUtils::increase).toList();
    }

    @SneakyThrows
    public List<Integer> process2(List<Integer> values) {
        ExecutorService executor = Executors.newFixedThreadPool(5);
        try {
            Map<Integer, Future<Integer>> futures = new LinkedHashMap<>();


            for (int value : values) {
                futures.put(value, executor.submit(() -> SizeUtils.increase(value)));
            }
            List<Integer> result = new ArrayList<>();
            for (int value : futures.keySet()) {
                Future<Integer> future = futures.get(value);
                result.add(future.get());
            }
            return result;
        } finally {
            executor.shutdownNow();
        }
    }
}
