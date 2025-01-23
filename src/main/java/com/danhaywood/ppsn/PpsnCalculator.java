package com.danhaywood.ppsn;

import java.util.stream.IntStream;

import org.springframework.stereotype.Service;

@Service
public class PpsnCalculator {

    static final int[] WEIGHTS = {8, 7, 6, 5, 4, 3, 2, 0, 9};
    static final String MAPPING = "WABCDEFGHIJKLMNOPQRSTUV";

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

        int checksum = IntStream.range(0, 9)
            .map(i -> checksumForChar(ppsn, i))
            .sum();

        return MAPPING.charAt(checksum % 23);
    }

    private static int checksumForChar(String ppsn, int i) {
        return (i == 8
                    ? ppsn.length() == 9 && ppsn.charAt(i) != 'W'
                        ? MAPPING.indexOf(ppsn.charAt(i))
                        : 0
                    : Character.getNumericValue(ppsn.charAt(i))
                ) * WEIGHTS[i];
    }
}
