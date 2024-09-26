package com.ad.wsd;

import org.junit.jupiter.api.Test;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CertificateUpdateGeneratorTest {

    @Test
    public void generateQuotes() {
        CertificateUpdateGenerator certificateUpdateGenerator = new CertificateUpdateGenerator(10, 100);
        Stream<String> quotes = certificateUpdateGenerator.generateQuotes();
        assertNotNull(quotes);
        assertEquals(10 * 100, quotes.count());
    }

    @Test
    public void generateQuotesWithZeroThreads() {
        CertificateUpdateGenerator certificateUpdateGenerator = new CertificateUpdateGenerator(0, 100);
        assertThrows(IllegalArgumentException.class, certificateUpdateGenerator::generateQuotes);
    }

    @Test
    public void generateQuotesWithZeroQuotes() {
        CertificateUpdateGenerator certificateUpdateGenerator = new CertificateUpdateGenerator(10, 0);
        Stream<String> quotes = certificateUpdateGenerator.generateQuotes();
        assertNotNull(quotes);
        assertEquals(0, quotes.count());
    }

    @Test
    public void generateQuotesWithNegativeThreads() {
        CertificateUpdateGenerator certificateUpdateGenerator = new CertificateUpdateGenerator(-1, 100);
        assertThrows(IllegalArgumentException.class, certificateUpdateGenerator::generateQuotes);
    }

    @Test
    public void generateQuotesWithNegativeQuotes() {
        CertificateUpdateGenerator certificateUpdateGenerator = new CertificateUpdateGenerator(10, -1);
        assertThrows(IllegalArgumentException.class, certificateUpdateGenerator::generateQuotes);
    }

    @Test
    public void generateQuotesWithZeroThreadsAndZeroQuotes() {
        CertificateUpdateGenerator certificateUpdateGenerator = new CertificateUpdateGenerator(0, 0);
        assertThrows(IllegalArgumentException.class, certificateUpdateGenerator::generateQuotes);
    }

    @Test
    public void generateQuotesWithNegativeThreadsAndNegativeQuotes() {
        CertificateUpdateGenerator certificateUpdateGenerator = new CertificateUpdateGenerator(-1, -1);
        assertThrows(IllegalArgumentException.class, certificateUpdateGenerator::generateQuotes);
    }
}
