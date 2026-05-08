package com.swissre;

import java.math.BigDecimal;

import static com.swissre.RentalConstants.*;

public enum VehicleType {
    COMPACT_VAN(RATE_COMPACT_PER_KM, FUEL_PER_LITER, false),
    LARGE_VAN(RATE_LARGE_PER_KM, FUEL_PER_LITER, false),
    E_VAN(RATE_E_PER_KM, ELECTRICITY_PER_KWH, true);

    public final BigDecimal perKmRate;
    public final BigDecimal energyRate;
    public final boolean isElectric;

    VehicleType(BigDecimal perKmRate, BigDecimal energyRate, boolean isElectric) {
        this.perKmRate = perKmRate;
        this.energyRate = energyRate;
        this.isElectric = isElectric;
    }
}
