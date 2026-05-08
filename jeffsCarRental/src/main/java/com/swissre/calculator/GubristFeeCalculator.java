package com.swissre.calculator;

import com.swissre.CostComponent;
import com.swissre.RentalRecord;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;

/**
 * Calculates the Gubrist Tunnel fee based on the number of passages.
 * <p>
 * Cost: CHF 2.50 per passage.
 * After two paid passages per day, any additional passages are free.
 */
public class GubristFeeCalculator implements CostCalculator {
    private static final BigDecimal GUBRIST_FEE = new BigDecimal("2.50");
    private static final int MAX_PAID_PASSAGES = 2;

    @Override
    public Optional<CostComponent> calculate(RentalRecord record) {
        if (record.gubristPassages() > 0) {
            int paidPassages = Math.min(record.gubristPassages(), MAX_PAID_PASSAGES);
            BigDecimal cost = GUBRIST_FEE.multiply(BigDecimal.valueOf(paidPassages))
                    .setScale(2, RoundingMode.HALF_UP);

            var label = String.format("Gubrist Tunnel Fee (paid %d of %d passages)",
                    paidPassages, record.gubristPassages());

            return Optional.of(new CostComponent(label, cost));
        }
        return Optional.empty();
    }
}
