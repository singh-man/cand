package com.swissre;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RentalServiceTest {

    @Test
    @DisplayName("Compact Van with Base and Fuel")
    void test_CompactVan_Base_AndFuel() {
        RentalRecord rentalRecord = new RentalRecord(
                VehicleType.COMPACT_VAN, 50, bd("6"), false, 0, 0
        );
        BigDecimal cost = RentalService.calculateCost(rentalRecord, 0);

        // Base: 50 x 0.82 = 41.00
        // Fuel: 6 x 1.95 = 11.70
        assertEquals(bd("52.70"), cost);
    }

    @Test
    @DisplayName("Compact Van with Base Distance greater than 80 and Fuel")
    void test_CompactVan_Base_DistanceGreater80_AndFuel() {
        RentalRecord rentalRecord = new RentalRecord(
                VehicleType.COMPACT_VAN, 100, bd("6"), false, 0, 0
        );
        BigDecimal cost = RentalService.calculateCost(rentalRecord, 0);

        // Base: 100 x 0.82 = 82.00
        // Fuel: 6 x 1.95 = 11.70
        assertEquals(bd("93.70"), cost);
    }

    @Test
    @DisplayName("Compact Van with Base Distance greater than 80 and Fuel")
    void test_CompactVan_Base_DistanceIs0_AndFuel() {
        RentalRecord rentalRecord = new RentalRecord(
                VehicleType.COMPACT_VAN, 0, bd("6"), false, 0, 0
        );
        BigDecimal cost = RentalService.calculateCost(rentalRecord, 0);

        // Base: 0 x 0.82 = 0.00
        // Fuel: 6 x 1.95 = 11.70
        assertEquals(bd("11.70"), cost);
    }

    @Test
    @DisplayName("Large Van with Base, Fuel and city congestion")
    void test_LargeVan_WithCityCongestion() {
        RentalRecord rentalRecord = new RentalRecord(
                VehicleType.LARGE_VAN, 100, bd("8"), false, 0, 25
        );
        BigDecimal cost = RentalService.calculateCost(rentalRecord, 0);

        // Base: 100 x 1.05 = 105.00
        // Fuel: 8 x 1.95 = 15.60
        // City: 25 x 1.00 = 25.00
        assertEquals(bd("145.60"), cost);
    }

    @Test
    @DisplayName("E-Van without EcoBonus and low distance")
    void test_EVan_WithoutEcoBonus_LowDistance() {
        RentalRecord rentalRecord = new RentalRecord(
                VehicleType.E_VAN, 80, bd("10"), false, 0, 0
        );
        BigDecimal cost = RentalService.calculateCost(rentalRecord, 0);

        // Base: 80 x 0.68 = 54.40
        // Energy: 10 x 0.30 = 3.00
        // Total: 57.40
        assertEquals(bd("57.40"), cost);
    }

    @Test
    @DisplayName("E-Van without EcoBonus and High Consumption")
    void test_EVan_WithoutEcoBonus_HighConsumption() {
        RentalRecord rentalRecord = new RentalRecord(
                VehicleType.E_VAN, 100, bd("22"), false, 0, 0
        );
        BigDecimal cost = RentalService.calculateCost(rentalRecord, 0);

        // Base: 100 x 0.68 = 68.00
        // Energy: 22 x 0.30 = 6.60
        // Total: 74.60
        // Consumption is 22 kWh/100km, which is NOT < 22.
        assertEquals(bd("74.60"), cost);
    }

    @Test
    @DisplayName("Gubrist Tunnel capping at two (more than 2)")
    void testGubristTunnelCappingAtTwo() {
        RentalRecord rentalRecord = new RentalRecord(
                VehicleType.COMPACT_VAN, 20, bd("3"), false, 5, 0
        );
        BigDecimal cost = RentalService.calculateCost(rentalRecord, 0);

        // Base: 16.40
        // Fuel: 5.85
        // Tunnel: min(5, 2) * 2.50 = 5.00
        assertEquals(bd("27.25"), cost);
    }

    @Test
    @DisplayName("Gubrist Tunnel Zero Passages")
    void test_GubristTunnel_ZeroPassages() {
        RentalRecord rentalRecord = new RentalRecord(
                VehicleType.COMPACT_VAN, 20, bd("3"), false, 0, 0
        );
        BigDecimal cost = RentalService.calculateCost(rentalRecord, 0);

        assertEquals(bd("22.25"), cost);
    }

    @Test
    @DisplayName("EVan with City Congestion and Eco Bonus")
    void test_EVan_CityCongestion_EcoBonus() {
        RentalRecord rentalRecord = new RentalRecord(
                VehicleType.E_VAN, 100, bd("15"), false, 0, 25
        );
        BigDecimal cost = RentalService.calculateCost(rentalRecord, 0);

        // Base: 100 x 0.68 = 68.00
        // Energy: 15 x 0.30 = 4.50
        // City Congestion Fee (25.0 km × 1.00) = 25.00
        // Eco Bonus: distance 100 > 80, consumption (15/100*100) = 15 < 22. So -10.00
        // Total: 68 + 4.5 - 10 = 62.50
        assertEquals(bd("87.50"), cost);
    }

    @Test
    @DisplayName("E-Van with Eco Bonus")
    void test_EVan_WithEcoBonus() {
        RentalRecord rentalRecord = new RentalRecord(
                VehicleType.E_VAN, 100, bd("20"), false, 2, 0
        );
        BigDecimal cost = RentalService.calculateCost(rentalRecord, 0);

        // Base: 100 x 0.68 = 68.00
        // Energy: 20 x 0.30 = 6.00
        // Tunnel: 2 x 2.50 = 5.00
        // Eco bonus: -10.00
        // Total: 68 + 6 + 5 - 10 = 69.00
        assertEquals(bd("69.00"), cost);
    }

    @Test
    @DisplayName("E-Van with Eco Bonus and Vignette")
    void test_EVan_WithEcoBonus_AndVignette() {
        RentalRecord rentalRecord = new RentalRecord(
                VehicleType.E_VAN, 100, bd("20"), true, 2, 0
        );
        BigDecimal cost = RentalService.calculateCost(rentalRecord, 0);

        // Base: 100 x 0.68 = 68.00
        // Energy: 20 x 0.30 = 6.00
        // Motorway / Vignette: 9.00
        // Tunnel: 2 x 2.50 = 5.00
        // Eco bonus: -10.00
        // Total: 68 + 6 + 9 + 5 - 10 = 78.00
        assertEquals(bd("78.00"), cost);
    }

    @Test
    @DisplayName("LargeVan with negative distance must be set to 0 still Ubrist Passage and Vignette")
    void test_LargeVan_negativeMileage_Vignette() {
        RentalRecord rentalRecord = new RentalRecord(
                VehicleType.LARGE_VAN, -23.8d, null, true, 0, 0);
        BigDecimal cost = RentalService.calculateCost(rentalRecord, 0);

        // Base: 0 x 0.68 = 0.00
        // Energy: 0 x 0.30 = 0.00
        // Motorway / Vignette: 9.00
        assertEquals(bd("9.00"), cost);
    }

    @Test
    @DisplayName("LargeVan with negative city Mileage must be set to 0 still Ubrist Passage and Vignette")
    void test_LargeVan_negativeCityMileage_ubristPassage_Vignette() {
        RentalRecord rentalRecord = new RentalRecord(
                VehicleType.LARGE_VAN, 50.8, null, true, 2, -56.7);
        BigDecimal cost = RentalService.calculateCost(rentalRecord, 0);

        // Base: 50.8 x 1.05 = 53.34
        // Energy: 0 x 0.30 = 0.00
        // Motorway / Vignette: 9.00
        // Tunnel: 2 x 2.50 = 5.00
        // Total: 53.34 + 9 + 5 = 67.34
        assertEquals(bd("67.34"), cost);
    }

    public static BigDecimal bd(String s) {
        return new BigDecimal(s);
    }
}
