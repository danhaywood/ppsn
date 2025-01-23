package com.danhaywood.ppsn;

import org.springframework.stereotype.Service;

@Service
public class PpsnCalculator {

    final static int[] REGULAR_CHARACTER_WEIGHTS = {8, 7, 6, 5, 4, 3, 2};
    public static final int NINTH_CHARACTER_WEIGHT = 9;
    static final String NINTH_DIGIT_DECODE        = "WABCDEFGHIJKLMNOPQRSTUV";
    static final String CHECKSUM_REMAINDER_ENCODE = "WABCDEFGHIJKLMNOPQRSTUV";

    public boolean isValid(String ppsn) {
        try {
            char c = calculateChecksum(ppsn);
            return c == ppsn.charAt(7);
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    public char calculateChecksum(String ppsn) {
        if(ppsn.length() < 8 || ppsn.length() > 9) {
            throw new IllegalArgumentException("PPSN must be between 8 and 9 characters long");
        }

        int checksum = 0;
        for (int i = 0; i < 7; i++) {
            checksum += Character.getNumericValue(ppsn.charAt(i)) * REGULAR_CHARACTER_WEIGHTS[i];
        }
        checksum = adjustIfPost2013(ppsn, checksum);

        int remainder = checksum % 23;
        return CHECKSUM_REMAINDER_ENCODE.charAt(remainder);
    }

    private static int adjustIfPost2013(String ppsn, int checksum) {
        if (ppsn.length() == 9) {
            int i = NINTH_DIGIT_DECODE.indexOf(ppsn.charAt(8));
            if (i == -1) {
                throw new IllegalArgumentException("Invalid PPSN");
            }
            checksum += i * NINTH_CHARACTER_WEIGHT;
        }
        return checksum;
    }
}
