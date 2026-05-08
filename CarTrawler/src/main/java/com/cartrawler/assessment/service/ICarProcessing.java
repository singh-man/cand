package com.cartrawler.assessment.service;

import com.cartrawler.assessment.car.CarResult;
import com.cartrawler.assessment.details.CorporateSupplier;
import com.cartrawler.assessment.filter.ICarsFilter;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;

public interface ICarProcessing {

    /**
     * Standard way as provided in the requirement
     * @param cars
     * @param chainedOprs
     * @return
     */
    List<CarResult> processCars(Collection<CarResult> cars, ICarsFilter chainedOprs);

    /**
     * This can be used to customize the operations by following the function chaining idea
     * @param cars
     * @param chainedOprs
     * @return
     */
    List<CarResult> processCarsDecorated(Collection<CarResult> cars,
                                         Function<Collection<CarResult>, Collection<CarResult>> chainedOprs);

    /**
     * As per default requirements
     */
    static Comparator<CarResult> compare() {
        return Comparator
                .comparing((CarResult e) -> !CorporateSupplier.isCorporate(e.supplierName()))
                .thenComparing(e -> e.getCarCategory())
                .thenComparingDouble(e -> e.rentalCost());
    }
}
