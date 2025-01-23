package com.danhaywood.ppsn;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class PpsnCalculator_Tests {

    @Autowired
    PpsnCalculator ppsnCalculator;

    @ParameterizedTest
    @EnumSource(Scenario.class)
    void testAddition(Scenario scenario) {

        char checksum = ppsnCalculator.calculateChecksum(scenario.ppsn);
        Assertions.assertThat(checksum).isEqualTo(scenario.checksum);

        boolean isValid = ppsnCalculator.isValid(scenario.ppsn);
        Assertions.assertThat(isValid).isEqualTo(scenario.valid);
    }

    public enum Scenario {

        REGULAR_0		 		("1234567T", 'T', true),
        REGULAR_0_INVALID		("1234567P", 'T', false),
        REGULAR_1		 		("2680737S", 'S', true),
        REGULAR_2		 		("7227485F", 'F', true),
        REGULAR_3		 		("1819002D", 'D', true),
        REGULAR_4		 		("7604924G", 'G', true),

        PRE_1999_WIFE_1			("6120218BW", 'B', true),
        PRE_1999_WIFE_1_INVALID	("6120218VW", 'B', false),
        PRE_1999_WIFE_2			("0465818BW", 'B', true),
        PRE_1999_WIFE_3			("0111218VW", 'V', true),
        PRE_1999_WIFE_4			("0566197HW", 'H', true),

        POST_2013_0				("1234567FA", 'F', true),
        POST_2013_0_INVALID		("1234567XA", 'F', false),
        POST_2013_1				("9493822MA", 'M', true),
        POST_2013_2				("1138716JA", 'J', true),
        POST_2013_3				("0018583VB", 'V', true),
        POST_2013_4				("0153613KB", 'K', true),
        ;

        private final String ppsn;
        private final char checksum;
        private final boolean valid;

        Scenario(String ppsn, char checksum, boolean valid) {
            this.ppsn = ppsn;
            this.checksum = checksum;
            this.valid = valid;
        }
    }
}
