package com.swissre;

import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class RentalServiceTest {

    @Test
    void testFullGeneration() {
        RentalService service = new RentalService();
        
        RentalRecord evan = new RentalRecord(VehicleType.E_VAN, 95.0, new BigDecimal("20.0"), true, 3, 0.0);
        RentalRecord compact = new RentalRecord(VehicleType.COMPACT_VAN, 40.0, new BigDecimal("5.0"), false, 0, 0.0);
        RentalRecord large = new RentalRecord(VehicleType.LARGE_VAN, 180.0, new BigDecimal("15.0"), false, 0, 30.0);

        String result = service.generateReceipt(List.of(evan, compact, large));
        
        assertThat(result).contains("JEFF'S CAR RENTAL - RECEIPT");
        assertThat(result).contains("GRAND TOTAL");
        assertThat(result).contains("Vehicle: 1: E_VAN");
        assertThat(result).contains("Vehicle: 2: COMPACT_VAN");
        assertThat(result).contains("Vehicle: 3: LARGE_VAN");
        assertThat(result).contains("Eco-bonus");
    }
}
