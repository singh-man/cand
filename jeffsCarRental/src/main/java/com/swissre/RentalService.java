package com.swissre;

import com.swissre.calculator.*;
import com.swissre.format.ConsoleReceiptFormatter;
import com.swissre.format.ReceiptFormatter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RentalService {

    private final RentalCostCalculator calculator;
    private final ReceiptFormatter formatter;

    public RentalService() {
        this.calculator = new RentalCostCalculator(Arrays.asList(
                new BaseDistanceCalculator(),
                new EnergyCostCalculator(),
                new CityCongestionCalculator(),
                new GubristFeeCalculator(),
                new VignetteCalculator(),
                new EcoBonusCalculator()
        ));
        this.formatter = new ConsoleReceiptFormatter();
    }

    public RentalService(RentalCostCalculator calculator, ReceiptFormatter formatter) {
        this.calculator = calculator;
        this.formatter = formatter;
    }

    public String generateReceipt(List<RentalRecord> records) {
        List<VehicleSummary> summaries = new ArrayList<>();
        BigDecimal grandTotal = BigDecimal.ZERO;

        for (int i = 0; i < records.size(); i++) {
            VehicleSummary summary = calculator.calculateSummary(records.get(i), i + 1);
            summaries.add(summary);
            grandTotal = grandTotal.add(summary.subtotal());
        }

        SummaryReceipt receipt = new SummaryReceipt(summaries, grandTotal);
        return formatter.format(receipt);
    }
}
