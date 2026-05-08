package com.swissre;

import java.io.PrintWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;

import static com.swissre.RentalConstants.*;
import static com.swissre.RentalFormatUtils.*;

public class RentalService {

    // Prevent from instantiation
    private RentalService() {}

    public static BigDecimal calculateCost(RentalRecord rentalRecord, int i) {
        PrintWriter out = new PrintWriter(System.out, true);

        VehicleType type = rentalRecord.getType();
        out.println("Vehicle: " + (i + 1) + ": " + type);

        // Base cost
        BigDecimal base = money(type.perKmRate.multiply(BigDecimal.valueOf(rentalRecord.getDistanceKm())));
        line(out, "Base distance (" + rentalRecord.getDistanceKm() + " km × " + fmtRate(type.perKmRate) + ")", base);

        // Energy cost
        BigDecimal energyCost = money(rentalRecord.getEnergyUsed().multiply(type.isElectric ? ELECTRICITY_PER_KWH : FUEL_PER_LITER));
        if (type.isElectric) {
            line(out, "Electricity (" + fmtQty(rentalRecord.getEnergyUsed()) + " kWh × " + fmtRate(ELECTRICITY_PER_KWH) + ")", energyCost);
        } else {
            line(out, "Fuel (" + fmtQty(rentalRecord.getEnergyUsed()) + " L × " + fmtRate(FUEL_PER_LITER) + ")", energyCost);
        }

        // Congestion
        BigDecimal cityFee = money(CITY_FEE_PER_KM.multiply(BigDecimal.valueOf(rentalRecord.getCityKm())));
        if (rentalRecord.getCityKm() > 0) {
            line(out, "City of Zurich Congestion Fee (" + rentalRecord.getCityKm() + " km × " + fmtRate(CITY_FEE_PER_KM) + ")", cityFee);
        }

        // Gubrist tunnel
        int paidPassages = Math.min(rentalRecord.getGubristPassages(), GUBRIST_FREE_AFTER_PAID);
        BigDecimal gubrist = money(GUBRIST_FEE.multiply(BigDecimal.valueOf(paidPassages)));
        if (rentalRecord.getGubristPassages() > 0) {
            String label = "Gubrist Tunnel Fee (paid " + paidPassages + " of " + rentalRecord.getGubristPassages() + " passages)";
            line(out, label, gubrist);
        }

        // Eco bonus
        BigDecimal ecoBonus = computeEcoBonus(rentalRecord);
        if (ecoBonus.signum() != 0) {
            // Show as negative on receipt
            line(out, "Eco-bonus", money(ecoBonus.negate()));
        }

        // Motorway vignette
        BigDecimal vignette = rentalRecord.isUsedMotorway() ? MOTORWAY_VIGNETTE : bd("0.00");
        if (vignette.signum() != 0) {
            line(out, "Motorway Day Vignette", money(vignette));
        }

        BigDecimal subTotal = money(base.add(energyCost).add(vignette).add(gubrist).add(cityFee).subtract(ecoBonus));
        out.println("  ---------------------------------------------------------------");
        line(out, "Subtotal", subTotal);

        return subTotal;
    }

    private static BigDecimal computeEcoBonus(RentalRecord rentalRecord) {
        // E-Van eco-bonus:
        // If drives > 80 km and uses < 22 kWh per 100 km -> subtract CHF 10.00
        if (rentalRecord.getType() != VehicleType.E_VAN) return bd("0.00");
        if (rentalRecord.getDistanceKm() <= 80) return bd("0.00");
        if (rentalRecord.getDistanceKm() == 0) return bd("0.00");

        BigDecimal per100 = rentalRecord.getEnergyUsed()
                .multiply(bd("100"))
                .divide(BigDecimal.valueOf(rentalRecord.getDistanceKm()), 6, RoundingMode.HALF_UP);

        return (per100.compareTo(bd("22.0")) < 0) ? ECO_BONUS : bd("0.00");
    }
}
