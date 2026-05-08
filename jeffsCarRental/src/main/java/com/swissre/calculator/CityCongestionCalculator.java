package com.swissre.calculator;

import com.swissre.CostComponent;
import com.swissre.RentalRecord;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;

/**
 * Calculates the City of Zurich Congestion Fee.
 * <p>
 * Cost: CHF 1.00 per km driven inside city limits.
 * Note: This does not apply to motorway driving.
 */
public class CityCongestionCalculator implements CostCalculator {
    private static final BigDecimal CITY_FEE_PER_KM = new BigDecimal("1.00");

    @Override
    public Optional<CostComponent> calculate(RentalRecord record) {
        if (record.cityKm() > 0) {
            BigDecimal cost = CITY_FEE_PER_KM.multiply(BigDecimal.valueOf(record.cityKm()))
                    .setScale(2, RoundingMode.HALF_UP);

            var label = String.format("City of Zurich Congestion Fee (%.1f km × CHF %.2f)",
                    record.cityKm(), CITY_FEE_PER_KM);

            return Optional.of(new CostComponent(label, cost));
        }
        return Optional.empty();
    }
}
