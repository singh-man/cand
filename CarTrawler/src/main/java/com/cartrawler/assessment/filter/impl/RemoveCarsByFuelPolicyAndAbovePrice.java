package com.cartrawler.assessment.filter.impl;

import com.cartrawler.assessment.car.CarResult;
import com.cartrawler.assessment.filter.ICarsFilter;

import java.util.*;
import java.util.function.BiPredicate;
import java.util.stream.Collectors;

public class RemoveCarsByFuelPolicyAndAbovePrice implements ICarsFilter {

    private CarResult.FuelPolicy policy;
    private BiPredicate<CarResult, List<CarResult>> priceFilter;

    public RemoveCarsByFuelPolicyAndAbovePrice(CarResult.FuelPolicy policy,
                                               BiPredicate<CarResult, List<CarResult>> priceFilter) {
        this.policy = policy;
        this.priceFilter = priceFilter;
    }

    @Override
    public Collection<CarResult> apply(Collection<CarResult> carResults) {
        Objects.requireNonNull(carResults);

        // Group cars by Corporate status and Category
        Map<String, List<CarResult>> groups = new HashMap<>();
        for (CarResult carResult : carResults) {
            groups.computeIfAbsent(getGroupKey(carResult), k -> new ArrayList<>()).add(carResult);
        }

        return carResults.stream()
                .filter(e -> e.fuelPolicy() != policy)
                .filter(car -> priceFilter.test(car, groups.get(getGroupKey(car))))
                .collect(Collectors.toList());
    }

    /**
     * Confusion in group
     * Generate group key for a car (Corporate/Non-Corporate + Category)
     */
    private String getGroupKey(CarResult car) {
        String corpStatus = car.isCorporateSupplier() ? "CORPORATE" : "NON_CORPORATE";
        return corpStatus + "_" + car.getCarCategory();
    }
}