package com.swissre.calculator;

import com.swissre.CostComponent;
import com.swissre.RentalRecord;
import java.math.BigDecimal;
import java.util.Optional;

/**
 * Calculates the Motorway Day Vignette fee.
 * <p>
 * Required if the customer uses the motorway.
 * Cost: CHF 9.00
 */
public class VignetteCalculator implements CostCalculator {
    private static final BigDecimal VIGNETTE_COST = new BigDecimal("9.00");

    @Override
    public Optional<CostComponent> calculate(RentalRecord record) {
        if (record.usedMotorway()) {
            return Optional.of(new CostComponent("Motorway Day Vignette", VIGNETTE_COST));
        }
        return Optional.empty();
    }
}
