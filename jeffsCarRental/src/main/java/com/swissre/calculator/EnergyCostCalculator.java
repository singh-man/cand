package com.swissre.calculator;

import com.swissre.CostComponent;
import com.swissre.RentalRecord;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;

/**
 * Calculates the energy or fuel cost consumed by the vehicle during the rental.
 * <p>
 * Energy/Fuel Costs:
 * <ul>
 * <li>Fuel (Compact & Large Vans): CHF 1.95 per liter</li>
 * <li>Electricity (E-Van): CHF 0.30 per kWh</li>
 * </ul>
 */
public class EnergyCostCalculator implements CostCalculator {
    @Override
    public Optional<CostComponent> calculate(RentalRecord record) {
        if (record.energyUsed().compareTo(BigDecimal.ZERO) == 0)
            return Optional.empty();

        BigDecimal cost = record.vehicleType().getEnergyRate()
                .multiply(record.energyUsed())
                .setScale(2, RoundingMode.HALF_UP);

        var unit = record.vehicleType().isElectric() ? "kWh" : "L";
        var type = record.vehicleType().isElectric() ? "Electricity" : "Fuel";
        var label = String.format("%s (%.1f %s × CHF %.2f)",
                type, record.energyUsed().doubleValue(), unit, record.vehicleType().getEnergyRate());

        return Optional.of(new CostComponent(label, cost));
    }
}
