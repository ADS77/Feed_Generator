package com.ad.wsd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SpringBootApplication
public class WsdApplication {

    private static final Logger logger = LoggerFactory.getLogger(WsdApplication.class);

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        SpringApplication.run(WsdApplication.class, args);

        // Get the number of threads and updates from args or use defaults
        int numberOfThreads = args.length > 0 ? Integer.parseInt(args[0]) : 5;
        int numberOfUpdates = args.length > 1 ? Integer.parseInt(args[1]) : 100;

        // Validate input
        if (numberOfThreads <= 0 || numberOfUpdates <= 0) {
            logger.error("Invalid input: number of threads and number of updates must be greater than zero.");
            return;
        }

        // Create a thread pool
        ExecutorService executor = Executors.newFixedThreadPool(numberOfThreads);
        List<Future<String>> futures = new ArrayList<>();

        // Submit tasks to the thread pool
        for (int i = 0; i < numberOfUpdates; i++) {
            futures.add(executor.submit(new CertificateUpdateCallable()));
        }

        // Process the results
        int i = 0;
        for (Future<String> future : futures) {
            logger.info(i + ": " + future.get());
            i++;
        }

        // Shutdown the executor and wait for tasks to finish
        executor.shutdown();
        try {
            if (!executor.awaitTermination(60, TimeUnit.SECONDS)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }
}
