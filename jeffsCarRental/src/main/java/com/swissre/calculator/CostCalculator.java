package com.swissre.calculator;

import com.swissre.CostComponent;
import com.swissre.RentalRecord;
import java.util.Optional;

/**
 * Defines the contract for an individual cost component calculation strategy.
 * Implementations are responsible for determining whether a specific cost
 * is applicable to a given rental, and computing that cost.
 */
public interface CostCalculator {

    /**
     * Calculates the cost item for a given rental record.
     *
     * @param record Data regarding the rental (vehicle, distance, usage).
     * @return An Optional containing the computed {@link CostComponent} if
     *         applicable, or empty if this cost does not apply.
     */
    Optional<CostComponent> calculate(RentalRecord record);
}
