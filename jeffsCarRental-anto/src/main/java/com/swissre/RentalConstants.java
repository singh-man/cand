package com.swissre;

import java.math.BigDecimal;

import static com.swissre.RentalFormatUtils.bd;

public class RentalConstants {

    // Prevent from instantiation
    private RentalConstants() {}

    // ===== Pricing constants =====
    public static final BigDecimal RATE_COMPACT_PER_KM = bd("0.82");
    public static final BigDecimal RATE_LARGE_PER_KM = bd("1.05");
    public static final BigDecimal RATE_E_PER_KM = bd("0.68");

    public static final BigDecimal FUEL_PER_LITER = bd("1.95");
    public static final BigDecimal ELECTRICITY_PER_KWH = bd("0.30");

    public static final BigDecimal MOTORWAY_VIGNETTE = bd("9.00");
    public static final BigDecimal GUBRIST_FEE = bd("2.50");
    public static final BigDecimal CITY_FEE_PER_KM = bd("1.00");

    public static final BigDecimal ECO_BONUS = bd("10.00");

    public static final int GUBRIST_FREE_AFTER_PAID = 2; // after 2 paid passages, additional are free
}
