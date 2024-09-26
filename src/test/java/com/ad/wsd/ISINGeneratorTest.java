package com.ad.wsd;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ISINGeneratorTest {

    private ISINGenerator isinGenerator;

    @BeforeEach
    public void setUp() {
        isinGenerator = new ISINGenerator();
    }

    @Test
    public void testGenerateISINFormat() {
        String isin = isinGenerator.generateISIN();
        assertNotNull(isin, "Generated ISIN should not be null");
        assertEquals(12, isin.length(), "ISIN should be 12 characters long");
        assertTrue(isin.matches("[A-Z]{2}[A-Z0-9]{9}[0-9]"), "ISIN should match the format");
    }

    @Test
    public void testCheckDigitCorrectness() {
        String isin = isinGenerator.generateISIN();
        // Validate the check digit based on your logic (either by recalculating it)
        // Example:
        String mainIsin = isin.substring(0, 11);
        int expectedCheckDigit = isinGenerator.calculateCheckDigit(mainIsin);
        int actualCheckDigit = Character.getNumericValue(isin.charAt(11));
        assertEquals(expectedCheckDigit, actualCheckDigit, "Check digit should be correctly calculated");
    }
}
