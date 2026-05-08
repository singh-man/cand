package com.swissre.calculator;

import com.swissre.CostComponent;
import com.swissre.RentalRecord;
import com.swissre.VehicleType;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class BaseDistanceCalculatorTest {

    private final BaseDistanceCalculator calculator = new BaseDistanceCalculator();

    @Test
    void shouldCalculateCompactVanBaseDistance() {
        RentalRecord record = new RentalRecord(VehicleType.COMPACT_VAN, 100.0, BigDecimal.ZERO, false, 0, 0);
        Optional<CostComponent> result = calculator.calculate(record);

        assertThat(result).isPresent();
        assertThat(result.get().amount()).isEqualByComparingTo("82.00");
    }

    @Test
    void shouldCalculateLargeVanBaseDistance() {
        RentalRecord record = new RentalRecord(VehicleType.LARGE_VAN, 100.0, BigDecimal.ZERO, false, 0, 0);
        Optional<CostComponent> result = calculator.calculate(record);

        assertThat(result).isPresent();
        assertThat(result.get().amount()).isEqualByComparingTo("105.00");
    }

    @Test
    void shouldCalculateEVanBaseDistance() {
        RentalRecord record = new RentalRecord(VehicleType.E_VAN, 100.0, BigDecimal.ZERO, false, 0, 0);
        Optional<CostComponent> result = calculator.calculate(record);

        assertThat(result).isPresent();
        assertThat(result.get().amount()).isEqualByComparingTo("68.00");
    }

    @Test
    void shouldReturnEmptyWhenDistanceIsZero() {
        RentalRecord record = new RentalRecord(VehicleType.COMPACT_VAN, 0.0, BigDecimal.ZERO, false, 0, 0);
        Optional<CostComponent> result = calculator.calculate(record);

        assertThat(result).isEmpty();
    }
}
