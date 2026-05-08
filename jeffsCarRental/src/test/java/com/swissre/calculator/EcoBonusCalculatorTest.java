package com.swissre.calculator;

import com.swissre.CostComponent;
import com.swissre.RentalRecord;
import com.swissre.VehicleType;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class EcoBonusCalculatorTest {

    private final EcoBonusCalculator calculator = new EcoBonusCalculator();

    @Test
    void shouldApplyBonusForEVanEfficientDriving() {
        // greater than 80km, less than 22 kWh per 100km
        RentalRecord record = new RentalRecord(VehicleType.E_VAN, 100.0, new BigDecimal("20.0"), false, 0, 0);
        Optional<CostComponent> result = calculator.calculate(record);

        assertThat(result).isPresent();
        assertThat(result.get().amount()).isEqualByComparingTo("-10.00");
    }

    @Test
    void shouldNotApplyBonusIfEnergyTooHigh() {
        // exactly 22 per 100km or more
        RentalRecord record = new RentalRecord(VehicleType.E_VAN, 100.0, new BigDecimal("22.0"), false, 0, 0);
        Optional<CostComponent> result = calculator.calculate(record);

        assertThat(result).isEmpty();
    }

    @Test
    void shouldNotApplyBonusIfDistanceNotGreaterThan80() {
        RentalRecord record = new RentalRecord(VehicleType.E_VAN, 80.0, new BigDecimal("10.0"), false, 0, 0);
        Optional<CostComponent> result = calculator.calculate(record);

        assertThat(result).isEmpty();
    }

    @Test
    void shouldNotApplyBonusForNonElectricVehicle() {
        RentalRecord record = new RentalRecord(VehicleType.COMPACT_VAN, 100.0, new BigDecimal("10.0"), false, 0, 0);
        Optional<CostComponent> result = calculator.calculate(record);

        assertThat(result).isEmpty();
    }
}
