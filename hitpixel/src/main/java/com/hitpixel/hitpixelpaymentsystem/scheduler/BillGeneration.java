package com.hitpixel.hitpixelpaymentsystem.scheduler;

import com.hitpixel.hitpixelpaymentsystem.Constants;
import com.hitpixel.hitpixelpaymentsystem.service.IBillGenerationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
@EnableScheduling
public class BillGeneration {

    private final IBillGenerationService billGenerationService;

    /**
     * For the purpose of the interview we are not sending real emails
     * NOT USING JMS : It can be  better design to manage concurrency
     * Bills will go to negative value which will mean to refund to the customer, but better solution can be provided
     * Keeping it very simple for  scope of the interview
     */

    @Scheduled(cron = "@daily")
    public void dailyGenerateBill() {
        log.info("============ Started to process DAILY BILL  generation ========");
        billGenerationService.processByBillingInterval(Constants.DAILY);
    }

    @Scheduled(cron = "@monthly")
    public void monthlyGenerateBill() {
        log.info("============ Started to process MONTHLY BILL  generation ========");
        billGenerationService.processByBillingInterval(Constants.MONTHLY);
    }
}