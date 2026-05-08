package com.swissre;

import java.io.PrintWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class RentalFormatUtils {

    // Prevent from instantiation
    private RentalFormatUtils() {}

    // ===== Formatting helpers =====
    public static void line(PrintWriter out, String label, BigDecimal amount) {
        // dot leaders
        final int leftWidth = 52;
        String left = "  " + label;
        if (left.length() > leftWidth) left = left.substring(0, leftWidth - 1) + "…";

        int dots = Math.max(2, leftWidth - left.length());

        out.printf("%s%s CHF %8s%n", left, ".".repeat(dots), fmtMoney(amount));
    }

    public static BigDecimal bd(String s) {
        return new BigDecimal(s);
    }

    public static BigDecimal money(BigDecimal x) {
        return x.setScale(2, RoundingMode.HALF_UP);
    }

    public static String fmtMoney(BigDecimal x) {
        // Ensure two decimals, keep minus sign if any
        return x.setScale(2, RoundingMode.HALF_UP).toPlainString();
    }

    public static String fmtRate(BigDecimal x) {
        return x.setScale(2, RoundingMode.HALF_UP).toPlainString();
    }

    public static String fmtQty(BigDecimal x) {
        return x.setScale(2, RoundingMode.HALF_UP).toPlainString();
    }
}
