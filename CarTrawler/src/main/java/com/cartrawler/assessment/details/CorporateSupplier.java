package com.cartrawler.assessment.details;

import java.util.Arrays;

public enum CorporateSupplier {
    AVIS,
    BUDGET,
    ENTERPRISE,
    FIREFLY,
    HERTZ,
    SIXT,
    THRIFTY;

    public static boolean isCorporate(String supplierName) {
        return supplierName != null &&
                Arrays.stream(CorporateSupplier.values())
                        .anyMatch(e -> e.name().equalsIgnoreCase(supplierName));
    }
}