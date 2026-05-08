package com.swissre;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.swissre.RentalServiceTest.bd;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RentalCostSummaryTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    void setUp() {
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    void restore() {
        System.setOut(originalOut);
    }

    @Test
    void testPrintSummaryWithEmptyList() {
        RentalCostSummary.printSummary(Collections.emptyList());
        String output = outContent.toString();

        assertTrue(output.contains("JEFF'S CAR RENTAL - RECEIPT"));
        assertTrue(output.contains("GRAND TOTAL (ALL VEHICLES): CHF 0.00"));
    }

    @Test
    @DisplayName("Print Summary with single record")
    void test_PrintSummary_WithSingleRecord() {
        RentalRecord rentalRecord = new RentalRecord(VehicleType.COMPACT_VAN, 100, bd("10"), false, 0, 0);
        // Cost = 100 * 0.82 + 10 * 1.95 = 82 + 19.5 = 101.50

        RentalCostSummary.printSummary(Collections.singletonList(rentalRecord));
        String output = outContent.toString();

        assertTrue(output.contains("GRAND TOTAL (ALL VEHICLES): CHF 101.50"));
    }

    @Test
    @DisplayName("Print Summary with two records")
    void testPrintSummaryWithMultipleRecords() {
        RentalRecord record1 = new RentalRecord(VehicleType.COMPACT_VAN, 100, bd("10"), false, 0, 0); // 101.50
        RentalRecord record2 = new RentalRecord(VehicleType.E_VAN, 50, bd("5"), false, 0, 0); // 50 * 0.68 + 5 * 0.30 = 34 + 1.5 = 35.50
        // Total = 101.50 + 35.50 = 137.00

        List<RentalRecord> records = Arrays.asList(record1, record2);
        RentalCostSummary.printSummary(records);
        String output = outContent.toString();

        assertTrue(output.contains("GRAND TOTAL (ALL VEHICLES): CHF 137.00"));
    }
}