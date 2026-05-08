package com.cartrawler.assessment.car;

import com.cartrawler.assessment.details.CarCategory;
import com.cartrawler.assessment.details.CorporateSupplier;

import java.util.Objects;

public record CarResult(String description, // make
                        String supplierName, // company
                        String sippCode, // model
                        double rentalCost,
                        FuelPolicy fuelPolicy) {

    public enum FuelPolicy {
        FULLFULL,
        FULLEMPTY
    }

    /**
     * Check if supplier is corporate as mentioned in @CorporateSupplier
     */
    public boolean isCorporateSupplier() {
        return CorporateSupplier.isCorporate(this.supplierName);
    }

    public CarCategory getCarCategory() {
        return CarCategory.getCarCategory(sippCode.charAt(0));
    }

    /**
     * Duplicate criteria: same make, model, supplier, SIPP, FuelPolicy
     */
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        CarResult carResult = (CarResult) o;
        return Objects.equals(description, carResult.description) &&
                Objects.equals(supplierName, carResult.supplierName) &&
                Objects.equals(sippCode, carResult.sippCode) &&
                Objects.equals(fuelPolicy, carResult.fuelPolicy);
    }

    @Override
    public int hashCode() {
        return Objects.hash(description, supplierName, sippCode, fuelPolicy);
    }

    public String toString() {
        return this.supplierName + " : " +
                this.description + " : " +
                this.sippCode + " : " +
                this.rentalCost + " : " +
                this.fuelPolicy;
    }
}
