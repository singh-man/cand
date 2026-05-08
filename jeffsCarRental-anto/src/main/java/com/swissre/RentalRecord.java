package com.swissre;

import java.math.BigDecimal;

import static com.swissre.RentalFormatUtils.bd;

public class RentalRecord {
    private final VehicleType type;
    private final double distanceKm;
    private final BigDecimal energyUsed;
    private final boolean usedMotorway;
    private final int gubristPassages;
    private final double cityKm;

    public RentalRecord(VehicleType type, double distanceKm, BigDecimal energyUsed,
                        boolean usedMotorway, int gubristPassages, double cityKm) {
        this.type = type;
        this.distanceKm = Math.max(0, distanceKm);
        this.energyUsed = energyUsed == null ? bd("0.00") : energyUsed.max(bd("0.00"));
        this.usedMotorway = usedMotorway;
        this.gubristPassages = Math.max(0, gubristPassages);
        this.cityKm = Math.max(0, cityKm);
    }

    public VehicleType getType() {
        return type;
    }

    public double getDistanceKm() {
        return distanceKm;
    }

    public BigDecimal getEnergyUsed() {
        return energyUsed;
    }

    public boolean isUsedMotorway() {
        return usedMotorway;
    }

    public int getGubristPassages() {
        return gubristPassages;
    }

    public double getCityKm() {
        return cityKm;
    }
}
