package com.swissre.calculator;

import com.swissre.CostComponent;
import com.swissre.RentalRecord;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;

/**
 * Calculates the base cost of the rental based on the distance driven and the
 * vehicle type.
 * <p>
 * Base Costs per Vehicle Type:
 * <ul>
 * <li>Compact Van: CHF 0.82 per km</li>
 * <li>Large Van: CHF 1.05 per km</li>
 * <li>E-Van: CHF 0.68 per km</li>
 * </ul>
 */
public class BaseDistanceCalculator implements CostCalculator {
    @Override
    public Optional<CostComponent> calculate(RentalRecord record) {
        if (record.distanceKm() == 0)
            return Optional.empty();

        BigDecimal cost = record.vehicleType().getBaseRate()
                .multiply(BigDecimal.valueOf(record.distanceKm()))
                .setScale(2, RoundingMode.HALF_UP);

        var label = String.format("Base distance (%.1f km × CHF %.2f)",
                record.distanceKm(), record.vehicleType().getBaseRate());

        return Optional.of(new CostComponent(label, cost));
    }
}
