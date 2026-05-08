package com.cartrawler.assessment.car;

import java.util.Comparator;

import static com.cartrawler.assessment.details.CorporateSupplier.isCorporate;

public class CarResultCustomComparator implements Comparator<CarResult> {

    @Override
    public int compare(CarResult car1, CarResult car2) {
        // 1. Corporate vs Non-Corporate (corporate first)
        boolean isCorp1 = isCorporate(car1.supplierName());
        boolean isCorp2 = isCorporate(car2.supplierName());

        if (isCorp1 != isCorp2) {
            return isCorp1 ? -1 : 1;
        }

        // Both are either of CORP or NON-CORP type
        // 2. Category (Mini, Economy, Compact, Other)
        int categoryCompare = car1.getCarCategory().compareTo(car2.getCarCategory());
        if (categoryCompare != 0) {
            return categoryCompare;
        }

        // 3. Price (low to high)
        return Double.compare(car1.rentalCost(), car2.rentalCost());
    }
}