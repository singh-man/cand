package com.cartrawler.assessment.service.impl;

import com.cartrawler.assessment.car.CarResult;
import com.cartrawler.assessment.filter.ICarsFilter;
import com.cartrawler.assessment.service.ICarProcessing;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

public class CarProcessingService implements ICarProcessing {

    private final Comparator<CarResult> comparator;

    public CarProcessingService(Comparator comparator) {
        this.comparator = comparator;
    }

    /**
     * Process car results: remove duplicates, sort, and optionally filter
     *
     * @param cars Input car collection
     * @param opr  additional operation to be done.
     * @return distinct -> sorted -> additional opr = car list
     */
    @Override
    public List<CarResult> processCars(Collection<CarResult> cars, ICarsFilter opr) {
        List<CarResult> sortedCars = cars.stream()
                .filter(Objects::nonNull)
                .distinct()         // 1. remove duplicates
                .sorted(comparator) // 2. Sort by business rules
                .toList();

        // Step 3: Optional - Remove fuel type FULLFULL cars
        if (opr != null) {
            sortedCars = opr.filterCars(sortedCars).stream().toList();
        }

//        Alternately
//        return processCarsDecorated(cars,
//                new DistinctCars()
//                .andThen(new SortedCars(ICarProcessing.compare()))
//                .andThen(opr));

        return sortedCars;
    }

    /**
     * Operation order can be customized
     * @param cars
     * @param chainedOprs
     * @return
     */
    @Override
    public List<CarResult> processCarsDecorated(Collection<CarResult> cars, Function<Collection<CarResult>, Collection<CarResult>> chainedOprs) {
        Objects.requireNonNull(cars);
        Objects.requireNonNull(chainedOprs);
        return chainedOprs.apply(cars).stream().toList();
    }
}