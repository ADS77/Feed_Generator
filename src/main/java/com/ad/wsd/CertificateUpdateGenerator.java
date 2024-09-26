package com.ad.wsd;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Stream;

public class CertificateUpdateGenerator {

    private final int threads;
    private final int quotes;

    public CertificateUpdateGenerator(int threads, int noOfUpdates) {
        this.threads = threads;
        this.quotes = noOfUpdates;
    }

    public Stream<String> generateQuotes() {
        ExecutorService executor = Executors.newFixedThreadPool(threads);
        List<Future<String>> futureList = new ArrayList<>();
        if (threads < 0 || quotes < 0) {
            throw new IllegalArgumentException("Threads and quotes must be greater than or equal 0");
        }
        try {
            for (int i = 0; i < quotes * threads; i++) {
                CertificateUpdateCallable callable = new CertificateUpdateCallable();
                Future<String> future = executor.submit(callable);
                futureList.add(future);
            }
            return futureList.stream().map(future -> {
                try {
                    return future.get();
                } catch (InterruptedException | ExecutionException e) {
                    throw new RuntimeException(e);
                }
            });
        } finally {
            shutdownExecutor(executor);
        }
    }


    private void shutdownExecutor(ExecutorService executor) {
        executor.shutdown();
        try {
            if (!executor.awaitTermination(60, TimeUnit.SECONDS)) {
                executor.shutdownNow();
                if (!executor.awaitTermination(60, TimeUnit.SECONDS)) {
                    System.err.println("Executor did not terminate!");
                }
            }
        } catch (InterruptedException ie) {
            executor.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }



}
