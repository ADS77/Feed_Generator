package com.ad.wsd;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CertificateUpdateCallableTest {

    private CertificateUpdateCallable certificateUpdateCallable;

    @BeforeEach
    public void setUp() {

        certificateUpdateCallable = new CertificateUpdateCallable();
    }

    @Test
    public void testCall() throws Exception {
        String update = certificateUpdateCallable.call();
        assertNotNull(update, "Certificate update should not be null");
        String[] parts = update.split(",");
        assertEquals(6, parts.length, "Certificate update should have 6 parts");

        // Test individual parts
        long timestamp = Long.parseLong(parts[0]);
        assertTrue(timestamp > 0, "Timestamp should be a valid number");

        String isin = parts[1];
        assertTrue(isin.matches("[A-Z]{2}[A-Z0-9]{9}[0-9]"), "ISIN should match the format");

        double bidPrice = Double.parseDouble(parts[2]);
        assertTrue(bidPrice >= 100.00 && bidPrice <= 200.00, "Bid price should be in range");

        int bidSize = Integer.parseInt(parts[3]);
        assertTrue(bidSize >= 1000 && bidSize <= 5000, "Bid size should be in range");

        double askPrice = Double.parseDouble(parts[4]);
        assertTrue(askPrice >= 100.00 && askPrice <= 200.00, "Ask price should be in range");

        int askSize = Integer.parseInt(parts[5]);
        assertTrue(askSize >= 1000 && askSize <= 10000, "Ask size should be in range");
    }
}
