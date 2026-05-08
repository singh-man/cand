package com.swissre;

import java.math.BigDecimal;
import java.util.List;

public record VehicleSummary(
    RentalRecord rentalRecord,
    int vehicleIndex,
    List<CostComponent> costComponents,
    BigDecimal subtotal
) {}
