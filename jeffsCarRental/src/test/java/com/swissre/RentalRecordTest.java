package com.swissre;

import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.assertThatCode;

class RentalRecordTest {
    @Test
    void testValidations() {
        assertThatIllegalArgumentException()
            .isThrownBy(() -> new RentalRecord(VehicleType.E_VAN, -1, BigDecimal.ZERO, false, 0, 0));
        
        assertThatIllegalArgumentException()
            .isThrownBy(() -> new RentalRecord(VehicleType.E_VAN, 10, new BigDecimal("-1.0"), false, 0, 0));

        assertThatIllegalArgumentException()
            .isThrownBy(() -> new RentalRecord(VehicleType.E_VAN, 10, BigDecimal.ZERO, false, -1, 0));

        assertThatIllegalArgumentException()
            .isThrownBy(() -> new RentalRecord(VehicleType.E_VAN, 10, BigDecimal.ZERO, false, 0, -1));
            
        assertThatCode(() -> new RentalRecord(VehicleType.E_VAN, 10, BigDecimal.ZERO, false, 0, 0))
            .doesNotThrowAnyException();
    }
}
