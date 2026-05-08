package com.swissre;

import java.math.BigDecimal;

public record RentalRecord(
    VehicleType vehicleType,
    double distanceKm,
    BigDecimal energyUsed,
    boolean usedMotorway,
    int gubristPassages,
    double cityKm
) {
    public RentalRecord {
        if (distanceKm < 0) throw new IllegalArgumentException("Distance cannot be negative");
        if (energyUsed == null || energyUsed.compareTo(BigDecimal.ZERO) < 0) 
            throw new IllegalArgumentException("Energy used must be non-negative");
        if (gubristPassages < 0) throw new IllegalArgumentException("Gubrist passages cannot be negative");
        if (cityKm < 0) throw new IllegalArgumentException("City km cannot be negative");
    }
}
