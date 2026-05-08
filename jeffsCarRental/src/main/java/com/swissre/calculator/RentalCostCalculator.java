package com.swissre.calculator;

import com.swissre.CostComponent;
import com.swissre.RentalRecord;
import com.swissre.VehicleSummary;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * A composite orchestrator that iterates over all registered {@link CostCalculator} strategies
 * to build the full {@link VehicleSummary} for a given rental.
 */
public class RentalCostCalculator {
    private final List<CostCalculator> componentCalculators;

    public RentalCostCalculator(List<CostCalculator> componentCalculators) {
        this.componentCalculators = componentCalculators;
    }

    public VehicleSummary calculateSummary(RentalRecord record, int vehicleIndex) {
        List<CostComponent> components = componentCalculators.stream()
                .map(calc -> calc.calculate(record))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();
                
        BigDecimal subtotal = components.stream()
                .map(CostComponent::amount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
                
        return new VehicleSummary(record, vehicleIndex, components, subtotal);
    }
}
