package com.swissre.calculator;

import com.swissre.CostComponent;
import com.swissre.RentalRecord;
import com.swissre.VehicleType;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class AdditionalCalculatorsTest {

    @Test
    void testCityCongestion() {
        CityCongestionCalculator calc = new CityCongestionCalculator();
        RentalRecord r1 = new RentalRecord(VehicleType.LARGE_VAN, 100, BigDecimal.ZERO, false, 0, 30);
        Optional<CostComponent> res1 = calc.calculate(r1);
        assertThat(res1).isPresent();
        assertThat(res1.get().amount()).isEqualByComparingTo("30.00");

        RentalRecord r2 = new RentalRecord(VehicleType.LARGE_VAN, 100, BigDecimal.ZERO, false, 0, 0);
        assertThat(calc.calculate(r2)).isEmpty();
    }

    @Test
    void testGubristFee() {
        GubristFeeCalculator calc = new GubristFeeCalculator();
        RentalRecord r1 = new RentalRecord(VehicleType.LARGE_VAN, 100, BigDecimal.ZERO, false, 3, 0);
        Optional<CostComponent> res1 = calc.calculate(r1);
        assertThat(res1).isPresent();
        // max 2 paid passages = 2 * 2.50 = 5.00
        assertThat(res1.get().amount()).isEqualByComparingTo("5.00");

        RentalRecord r2 = new RentalRecord(VehicleType.LARGE_VAN, 100, BigDecimal.ZERO, false, 1, 0);
        Optional<CostComponent> res2 = calc.calculate(r2);
        assertThat(res2.get().amount()).isEqualByComparingTo("2.50");

        RentalRecord r3 = new RentalRecord(VehicleType.LARGE_VAN, 100, BigDecimal.ZERO, false, 0, 0);
        assertThat(calc.calculate(r3)).isEmpty();
    }

    @Test
    void testVignette() {
        VignetteCalculator calc = new VignetteCalculator();
        RentalRecord r1 = new RentalRecord(VehicleType.LARGE_VAN, 100, BigDecimal.ZERO, true, 0, 0);
        assertThat(calc.calculate(r1)).isPresent().map(CostComponent::amount).hasValue(new BigDecimal("9.00"));

        RentalRecord r2 = new RentalRecord(VehicleType.LARGE_VAN, 100, BigDecimal.ZERO, false, 0, 0);
        assertThat(calc.calculate(r2)).isEmpty();
    }

    @Test
    void testEnergyCost() {
        EnergyCostCalculator calc = new EnergyCostCalculator();
        RentalRecord r1 = new RentalRecord(VehicleType.LARGE_VAN, 100, new BigDecimal("10.0"), false, 0, 0);
        assertThat(calc.calculate(r1)).isPresent().map(CostComponent::amount).hasValue(new BigDecimal("19.50"));

        RentalRecord r2 = new RentalRecord(VehicleType.E_VAN, 100, new BigDecimal("10.0"), false, 0, 0);
        assertThat(calc.calculate(r2)).isPresent().map(CostComponent::amount).hasValue(new BigDecimal("3.00"));

        RentalRecord r3 = new RentalRecord(VehicleType.LARGE_VAN, 100, BigDecimal.ZERO, false, 0, 0);
        assertThat(calc.calculate(r3)).isEmpty();
    }
}
