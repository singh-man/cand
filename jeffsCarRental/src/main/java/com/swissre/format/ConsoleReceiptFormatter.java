package com.swissre.format;

import com.swissre.CostComponent;
import com.swissre.SummaryReceipt;
import com.swissre.VehicleSummary;

public class ConsoleReceiptFormatter implements ReceiptFormatter {

    @Override
    public String format(SummaryReceipt receipt) {
        StringBuilder sb = new StringBuilder();
        sb.append("=================================================================\n");
        sb.append("                  JEFF'S CAR RENTAL - RECEIPT\n");
        sb.append("=================================================================\n");

        for (int i = 0; i < receipt.vehicleSummaries().size(); i++) {
            VehicleSummary vs = receipt.vehicleSummaries().get(i);
            sb.append(String.format("Vehicle: %d: %s\n", vs.vehicleIndex(), vs.rentalRecord().vehicleType()));

            for (CostComponent cc : vs.costComponents()) {
                String amountStr = String.format("CHF %.2f", cc.amount());
                sb.append(String.format("%-53s %10s\n", cc.label(), amountStr));
            }
            sb.append("  ---------------------------------------------------------------\n");
            
            String subTotalStr = String.format("CHF %.2f", vs.subtotal());
            sb.append(String.format("%-53s %10s\n", "Subtotal", subTotalStr));

            if (i < receipt.vehicleSummaries().size() - 1) {
                sb.append("\n_________________________________________________________________\n\n");
            }
        }

        sb.append("\n=================================================================\n");
        String grandTotalStr = String.format("CHF %.2f", receipt.grandTotal());
        sb.append(String.format("  GRAND TOTAL (ALL VEHICLES): %35s\n", grandTotalStr));
        sb.append("=================================================================\n");

        return sb.toString();
    }
}
