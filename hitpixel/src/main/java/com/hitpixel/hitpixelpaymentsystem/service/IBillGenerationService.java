package com.hitpixel.hitpixelpaymentsystem.service;

public interface IBillGenerationService {

    /**
     * Process to generate bill for all clients for the particular billingInterval
     * @param billingInterval billing interval, (daily or monthly)
     */
    void processByBillingInterval(final String billingInterval);

    /**
     * Process to generate bill for a client
     * @param clientName client name
     */
    void processByClient(String clientName);
}