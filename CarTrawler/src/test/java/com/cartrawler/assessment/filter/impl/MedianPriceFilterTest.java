package com.cartrawler.assessment.filter.impl;

import com.cartrawler.assessment.car.CarResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class MedianPriceFilterTest {

    private List<CarResult> testCars;
    private IsBelowMedianPrice price;

    @BeforeEach
    public void setUp() {
        price = new IsBelowMedianPrice();
        testCars = List.of(
                new CarResult("Volkswagen Polo", "NIZA", "EDMR", 12.81d, CarResult.FuelPolicy.FULLEMPTY),
                new CarResult("Ford C-Max Diesel", "NIZA", "CMMD", 22.04d, CarResult.FuelPolicy.FULLEMPTY),
                new CarResult("Renault Scenic Diesel", "NIZA", "JGAD", 93.67d, CarResult.FuelPolicy.FULLEMPTY),
                new CarResult("Volkswagen Up", "NIZA", "MDMR", 9.78d, CarResult.FuelPolicy.FULLEMPTY),
                new CarResult("Volkswagen Golf", "NIZA", "CDMR", 18.07d, CarResult.FuelPolicy.FULLEMPTY));
    }

    @Test
    public void testFilterCarBelowMedian() {
        CarResult carBelowMedian = testCars.get(0);

        boolean shouldKeep = price.test(carBelowMedian, testCars);

        assertTrue(shouldKeep, "Car priced below median should pass the filter");
    }
}
