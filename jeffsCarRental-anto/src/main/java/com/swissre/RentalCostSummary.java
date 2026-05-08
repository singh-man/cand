package com.swissre;

import java.io.PrintWriter;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static java.math.BigDecimal.ZERO;

public class RentalCostSummary {

    // Prevent from instantiation
    private RentalCostSummary() {}

    public static void printSummary(List<RentalRecord> records) {

        BigDecimal grandTotal = ZERO;
        PrintWriter out = new PrintWriter(System.out, true);

        String header = """
                =================================================================
                                  JEFF'S CAR RENTAL - RECEIPT
                                     Date: %s
                =================================================================
                """;
        out.printf(header, LocalDate.now());

        for (int i = 0; i < records.size(); i++) {
            BigDecimal subtotal = RentalService.calculateCost(records.get(i), i);
            grandTotal = grandTotal.add(subtotal);
            if (i < records.size() - 1) { // Don't print last line before summary
                out.println();
                out.println("_________________________________________________________________");
            }
        }

        out.println();
        String footer = """
                =================================================================
                  GRAND TOTAL (ALL VEHICLES): CHF %.2f
                =================================================================
                """;
        out.printf(footer, grandTotal);
        out.flush();
    }
}
