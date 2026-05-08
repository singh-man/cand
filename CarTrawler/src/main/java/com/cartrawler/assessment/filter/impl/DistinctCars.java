package com.cartrawler.assessment.filter.impl;

import com.cartrawler.assessment.car.CarResult;
import com.cartrawler.assessment.filter.ICarsFilter;

import java.util.Collection;
import java.util.Objects;

/**
 * Remove duplicates
 */
public class DistinctCars implements ICarsFilter {

    @Override
    public Collection<CarResult> apply(Collection<CarResult> carResults) {
        return Objects.requireNonNull(carResults)
                .stream()
                .distinct()
                .toList();
    }
}
