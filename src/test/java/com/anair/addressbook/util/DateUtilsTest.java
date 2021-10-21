package com.anair.addressbook.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class DateUtilsTest {

    @Test
    @DisplayName("Should convert 2-digit year 00 to 2000")
    void y2kTest() {
        assertThat(DateUtils.parseDateString("01/01/00"))
                .isEqualTo(LocalDate.of(2000, 1, 1));
    }

    @Test
    @DisplayName("Should convert 2-digit year 77 to 1977")
    void twentiethCenturyTest() {
        assertThat(DateUtils.parseDateString("01/01/77"))
                .isEqualTo(LocalDate.of(1977, 1, 1));
    }

    @Test
    @DisplayName("Should convert 2-digit year 21 to 2021")
    void currentCenturyTest() {
        assertThat(DateUtils.parseDateString("01/01/21"))
                .isEqualTo(LocalDate.of(2021, 1, 1));
    }
}
