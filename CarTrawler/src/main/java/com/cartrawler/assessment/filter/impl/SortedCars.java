package com.cartrawler.assessment.filter.impl;

import com.cartrawler.assessment.car.CarResult;
import com.cartrawler.assessment.filter.ICarsFilter;

import java.util.Collection;
import java.util.Comparator;
import java.util.Objects;

public class SortedCars implements ICarsFilter {

    private Comparator<CarResult> comparator;

    public SortedCars(Comparator<CarResult> comparator) {
        this.comparator = comparator;
    }

    @Override
    public Collection<CarResult> apply(Collection<CarResult> carResults) {
        return Objects.requireNonNull(carResults)
                .stream()
                .sorted(comparator)
                .toList();
    }
}