package com.swissre;

import java.math.BigDecimal;
import java.util.List;

public record SummaryReceipt(
    List<VehicleSummary> vehicleSummaries,
    BigDecimal grandTotal
) {}
