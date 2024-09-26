package com.ad.wsd;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class MultiThreadedCertificateUpdateTest {

    @Test
    public void testMultiThreadedCertificateUpdates() throws InterruptedException, ExecutionException {
        int numberOfThreads = 5;
        int numberOfUpdates = 10;

        ExecutorService executor = Executors.newFixedThreadPool(numberOfThreads);
        List<Future<String>> futures = new ArrayList<>();

        for (int i = 0; i < numberOfUpdates; i++) {
            futures.add(executor.submit(new CertificateUpdateCallable()));
        }

        for (Future<String> future : futures) {
            assertNotNull(future.get(), "Each certificate update should return a valid string");
        }

        executor.shutdown();
    }
}
