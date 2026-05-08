package com.swissre;

import java.math.BigDecimal;
import java.util.List;

public class JeffsCarRentalApp {
    public static void main(String[] args) {
        // Input Examples from PDF
        // E-Van, 95 km, 20 kWh, Motorway Vignette, Gubrist Tunnel x3
        RentalRecord evan = new RentalRecord(
                VehicleType.E_VAN, 95.0, new BigDecimal("20.0"), true, 3, 0.0);

        // Compact Van, 40 km, 5 liters
        RentalRecord compact = new RentalRecord(
                VehicleType.COMPACT_VAN, 40.0, new BigDecimal("5.0"), false, 0, 0.0);

        // Large Van, 180 km, 15 liters, City Congestion Fee (30 km city driving)
        RentalRecord large = new RentalRecord(
                VehicleType.LARGE_VAN, 180.0, new BigDecimal("15.0"), false, 0, 30.0);

        List<RentalRecord> rentals = List.of(evan, compact, large);

        RentalService service = new RentalService();
        String receipt = service.generateReceipt(rentals);

        System.out.println(receipt);
    }
}
