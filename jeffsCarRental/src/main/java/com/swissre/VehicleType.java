package com.swissre;

import java.math.BigDecimal;

public enum VehicleType {
    COMPACT_VAN(new BigDecimal("0.82"), new BigDecimal("1.95"), false),
    LARGE_VAN(new BigDecimal("1.05"), new BigDecimal("1.95"), false),
    E_VAN(new BigDecimal("0.68"), new BigDecimal("0.30"), true);

    private final BigDecimal baseRate;
    private final BigDecimal energyRate;
    private final boolean isElectric;

    VehicleType(BigDecimal baseRate, BigDecimal energyRate, boolean isElectric) {
        this.baseRate = baseRate;
        this.energyRate = energyRate;
        this.isElectric = isElectric;
    }

    public BigDecimal getBaseRate() { return baseRate; }
    public BigDecimal getEnergyRate() { return energyRate; }
    public boolean isElectric() { return isElectric; }
}
