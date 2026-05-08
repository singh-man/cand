package com.cartrawler.assessment.service.impl;

import com.cartrawler.assessment.car.CarResult;
import com.cartrawler.assessment.car.CarResultCustomComparator;
import com.cartrawler.assessment.filter.ICarsFilter;
import com.cartrawler.assessment.filter.impl.DistinctCars;
import com.cartrawler.assessment.filter.impl.IsBelowMedianPrice;
import com.cartrawler.assessment.filter.impl.RemoveCarsByFuelPolicyAndAbovePrice;
import com.cartrawler.assessment.filter.impl.SortedCars;
import com.cartrawler.assessment.service.ICarProcessing;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CarProcessingServiceTest {

    private CarProcessingService carProcessingService;

    @BeforeEach
    public void setUp() {
        carProcessingService = new CarProcessingService(new CarResultCustomComparator());
    }

    @Test
    public void testProcessCars_havingDuplicatesNoAdditionalOpr() {
        List<CarResult> cars = List.of(
                new CarResult("Volkswagen Polo", "NIZA", "EDMR", 12.81d, CarResult.FuelPolicy.FULLEMPTY),
                new CarResult("Volkswagen Polo", "NIZA", "EDMR", 12.81d, CarResult.FuelPolicy.FULLEMPTY));

        List<CarResult> result = carProcessingService.processCars(cars, null);

        assertEquals(1, result.size());
        assertEquals(cars.get(0), result.get(0));
    }

    @Test
    public void testProcessCars_havingDuplicatesWithAdditionalOpr() {
        List<CarResult> cars = List.of(
                new CarResult("Volkswagen Polo", "NIZA", "EDMR", 12.81d, CarResult.FuelPolicy.FULLEMPTY),
                new CarResult("Volkswagen Polo", "NIZA", "EDMR", 12.81d, CarResult.FuelPolicy.FULLEMPTY));

        // Returns all cars
        ICarsFilter noOpFilter = filteredCars -> filteredCars;

        List<CarResult> result = carProcessingService.processCars(cars, noOpFilter);

        assertEquals(1, result.size());
        assertEquals(cars.get(0), result.get(0));
    }

    @Test
    public void testProcessCars_SortsCorrectly() {

        CarResult nonCorpMini = new CarResult("NonCorp Car", "NIZA", "MDMR", 10.0d, CarResult.FuelPolicy.FULLEMPTY);

        CarResult corpMiniHighPrice = new CarResult("Corp Car Mini High Price", "AVIS", "MDMR", 20.0d, CarResult.FuelPolicy.FULLEMPTY);

        CarResult corpMiniLowPrice = new CarResult("Corp Car Mini Low Price", "AVIS", "MDMR", 2.0d, CarResult.FuelPolicy.FULLEMPTY);

        CarResult corpEconomyLowPrice = new CarResult("Corp Car Economy", "AVIS", "EDMR", 5.0d, CarResult.FuelPolicy.FULLEMPTY);

        List<CarResult> cars = List.of(
                nonCorpMini,
                corpMiniHighPrice,
                corpMiniLowPrice,
                corpEconomyLowPrice);

        ICarsFilter noOpFilter = filteredCars -> filteredCars;

        List<CarResult> result = carProcessingService.processCars(cars, noOpFilter);

        // Expected Order:
        // 1. Corporate Mini (Corp first, Mini < Economy) -> corpMiniLowPrice
        assertEquals(corpMiniLowPrice, result.get(0), "First should be Corporate Mini Low Price");
        // 2. Corporate Mini (Corp first, Mini < Economy) -> corpMiniHighPrice
        assertEquals(corpMiniHighPrice, result.get(1), "First should be Corporate Mini High Price");
        // 3. Corporate Economy (Corp first, Economy > Mini) -> corpEconomyLowPrice
        assertEquals(corpEconomyLowPrice, result.get(2), "Second should be Corporate Economy");
        // 4. Non-Corporate (Non-Corp last) -> nonCorpMini
        assertEquals(nonCorpMini, result.get(3), "Third should be Non-Corporate");
    }

    @Test
    public void testProcessCars_AppliesFilter() {
        CarResult carFullEmpty = new CarResult("Car 1", "NIZA", "EDMR", 10.0d, CarResult.FuelPolicy.FULLEMPTY); // NIZA is non-corp
        CarResult carFullFull = new CarResult("Car 2", "NIZA", "EDMR", 20.0d, CarResult.FuelPolicy.FULLFULL);
        List<CarResult> cars = List.of(carFullEmpty, carFullFull);

        // Filter valid only for FULLEMPTY
        ICarsFilter mock = Mockito.mock(ICarsFilter.class);
        Mockito.when(mock.filterCars(cars))
                .thenReturn(List.of(carFullEmpty));

        List<CarResult> result = carProcessingService.processCars(cars, mock);

        assertEquals(1, result.size());
        assertEquals(carFullEmpty, result.get(0));
    }

    @Test
    public void testProcessCarsDecorated() {
        List<CarResult> cars = List.of(
                new CarResult("Car 1", "NIZA", "EDMR", 10.0d, CarResult.FuelPolicy.FULLEMPTY),
                new CarResult("Car 1", "NIZA", "EDMR", 10.0d, CarResult.FuelPolicy.FULLEMPTY),
                new CarResult("Car 1", "NIZA", "EDMR", 5.0d, CarResult.FuelPolicy.FULLEMPTY),
                new CarResult("Car 2", "NIZA", "EDMR", 30.0d, CarResult.FuelPolicy.FULLFULL),
                new CarResult("Car 3", "NIZA", "EDMR", 5.0d, CarResult.FuelPolicy.FULLFULL));

        Function<Collection<CarResult>, Collection<CarResult>> chainedOprs =
                new RemoveCarsByFuelPolicyAndAbovePrice(CarResult.FuelPolicy.FULLEMPTY, new IsBelowMedianPrice())
                        .andThen(new DistinctCars())
                        .andThen(new SortedCars(ICarProcessing.compare()));

        List<CarResult> result = carProcessingService.processCarsDecorated(cars, chainedOprs);

        assertEquals(1, result.size());
        assertEquals(cars.get(4), result.get(0));
    }
}