package com.swissre.calculator;

import com.swissre.CostComponent;
import com.swissre.RentalRecord;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;

public class EcoBonusCalculator implements CostCalculator {
    private static final BigDecimal ECO_BONUS = new BigDecimal("-10.00");
    private static final double MIN_DISTANCE_KM = 80.0;
    private static final BigDecimal MAX_ENERGY_PER_100KM = new BigDecimal("22.0");

    @Override
    public Optional<CostComponent> calculate(RentalRecord record) {
        if (!record.vehicleType().isElectric()) return Optional.empty();
        if (record.distanceKm() <= MIN_DISTANCE_KM) return Optional.empty();
        
        BigDecimal per100Km = record.energyUsed()
                .multiply(new BigDecimal("100"))
                .divide(BigDecimal.valueOf(record.distanceKm()), 6, RoundingMode.HALF_UP);
                
        return per100Km.compareTo(MAX_ENERGY_PER_100KM) < 0
                ? Optional.of(new CostComponent("Eco-bonus", ECO_BONUS))
                : Optional.empty();
    }
}
