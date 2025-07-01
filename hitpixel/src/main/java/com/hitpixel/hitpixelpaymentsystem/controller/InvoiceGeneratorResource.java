package com.hitpixel.hitpixelpaymentsystem.controller;

import com.hitpixel.hitpixelpaymentsystem.ResponseHandler;
import com.hitpixel.hitpixelpaymentsystem.service.IBillGenerationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/invoice")
@RequiredArgsConstructor
@Slf4j
public class InvoiceGeneratorResource {

    private final IBillGenerationService billGenerationService;

    /**
     * Call the bill-generator
     * @param billingInterval billing-interval (daily or monthly)
     * @return response if bill generated or failure
     */
    @GetMapping(value = "/byInterval")
    public ResponseEntity<Object> generateInvoice(@RequestParam String billingInterval) {
        if (billingInterval.equals("daily")
                || billingInterval.equals("monthly")) {
            billGenerationService.processByBillingInterval(billingInterval);
        } else {
            return ResponseHandler.generateResponse("Invalid Billing-Interval provided (must be either daily or monthly) ", HttpStatus.BAD_REQUEST, null);
        }
        return ResponseHandler.generateResponse(billingInterval + " Bill-generated successfully" , HttpStatus.OK,null);
    }

    /**
     * Call the bill-generator by client name
     * @param clientName client name
     * @return response if bill generated or failed
     */
    @GetMapping(value = "/byClientName")
    public ResponseEntity<Object> generateInvoiceByClient(@RequestParam String clientName) {
        if (!clientName.isEmpty() ) {
            billGenerationService.processByClient(clientName);
        } else {
            return ResponseHandler.generateResponse("Invalid Billing-Interval provided (must be either daily or monthly) ", HttpStatus.BAD_REQUEST, null);
        }
        return ResponseHandler.generateResponse( " Bill-generated successfully for Client " + clientName , HttpStatus.OK,null);
    }
}