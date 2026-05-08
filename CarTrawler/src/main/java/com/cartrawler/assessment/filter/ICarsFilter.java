package com.cartrawler.assessment.filter;

import com.cartrawler.assessment.car.CarResult;

import java.util.Collection;
import java.util.function.Function;

/**
 * Strategy: Impl of this class
 * As it is Functional type
 * Function chains represent
 * Decorator and Template
 */
@FunctionalInterface
public interface ICarsFilter extends Function<Collection<CarResult>, Collection<CarResult>> {

    default Collection<CarResult> filterCars(Collection<CarResult> cars) {
        return this.apply(cars);
    }
}