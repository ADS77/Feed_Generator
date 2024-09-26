package com.ad.wsd;

import java.util.concurrent.ThreadLocalRandom;

public class ISINGenerator {
        private static final String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        private static final int ISIN_LENGTH = 12;

        public String generateISIN() {
            StringBuilder sb = new StringBuilder();
            // Generate first 2 random uppercase alphabets
            for (int i = 0; i < 2; i++) {
                sb.append(ALPHABET.charAt(ThreadLocalRandom.current().nextInt(ALPHABET.length())));
            }

            // Generate 9 random alphanumeric characters
            for (int i = 0; i < 9; i++) {
                if (ThreadLocalRandom.current().nextBoolean()) {
                    sb.append(ALPHABET.charAt(ThreadLocalRandom.current().nextInt(ALPHABET.length())));
                } else {
                    sb.append(ThreadLocalRandom.current().nextInt(10));
                }
            }

            // Calculate and append the check digit
            sb.append(calculateCheckDigit(sb.toString()));

            return sb.toString();
        }

        int calculateCheckDigit(String isin) {
            StringBuilder numericISIN = new StringBuilder();
            for (char ch : isin.toCharArray()) {
                if (Character.isDigit(ch)) {
                    numericISIN.append(ch);
                } else {
                    numericISIN.append((int) ch - 55);
                }
            }

            int sum = 0;
            boolean doubleDigit = true;
            for (int i = numericISIN.length() - 1; i >= 0; i--) {
                int digit = Character.getNumericValue(numericISIN.charAt(i));
                if (doubleDigit) {
                    digit *= 2;
                    if (digit > 9) {
                        digit -= 9;
                    }
                }
                sum += digit;
                doubleDigit = !doubleDigit;
            }
            return (10 - (sum % 10)) % 10;
        }




}
