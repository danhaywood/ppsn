package com.danhaywood.ppsn;

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

        int checksum = 0;
        for (int i = 0; i < 9; i++) {
            checksum += checksumForCharacter(ppsn, i);
        }

        int remainder = checksum % 23;
        return MAPPING.charAt(remainder);
    }

    private static int checksumForCharacter(String ppsn, int i) {
        switch (i) {
            case 7:
                // skip the checksum character
                return 0;
            case 8:
                if(ppsn.length() != 9 || ppsn.charAt(i) == 'W') {
                    return 0;
                }
                int val = MAPPING.indexOf(ppsn.charAt(8));
                return val * WEIGHTS[i];
            default:
                return Character.getNumericValue(ppsn.charAt(i)) * WEIGHTS[i];
        }
    }
}
