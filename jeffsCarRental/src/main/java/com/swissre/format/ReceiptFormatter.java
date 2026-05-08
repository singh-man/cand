package com.swissre.format;

import com.swissre.SummaryReceipt;

/**
 * Formats a given {@link SummaryReceipt} into a structured human-readable string output.
 */
public interface ReceiptFormatter {

    /**
     * Converts a fully evaluated receipt into its string representation.
     *
     * @param receipt The calculated receipt summary containing totals and line items.
     * @return Formatted string representation of the receipt.
     */
    String format(SummaryReceipt receipt);
}
