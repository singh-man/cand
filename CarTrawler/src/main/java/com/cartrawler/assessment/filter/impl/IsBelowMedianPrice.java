package com.cartrawler.assessment.filter.impl;

import com.cartrawler.assessment.car.CarResult;

import java.util.List;
import java.util.function.BiPredicate;
import java.util.stream.Collectors;

/**
 * If the inspected car price below the median among the group
 */
public class IsBelowMedianPrice implements BiPredicate<CarResult, List<CarResult>> {

    @Override
    public boolean test(CarResult car, List<CarResult> groupCars) {
        double medianPrice = calculateMedian(groupCars);
        return car.rentalCost() <= medianPrice;
    }

    private double calculateMedian(List<CarResult> cars) {
        if (cars == null || cars.isEmpty()) {
            return 0.0;
        }

        List<Double> prices = cars.stream()
                .map(CarResult::rentalCost)
                .sorted()
                .toList();

        int size = prices.size();
        return (size % 2 == 0) ?
                (prices.get(size / 2 - 1) + prices.get(size / 2)) / 2.0 :
                prices.get(size / 2);
    }
}