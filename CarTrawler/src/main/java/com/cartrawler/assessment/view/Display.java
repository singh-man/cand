package com.cartrawler.assessment.view;

import com.cartrawler.assessment.car.CarResult;
import java.util.Set;

public class Display {
    public void render(Set<CarResult> cars) {
        for (CarResult car : cars) {
            // System.out.println (car);
            System.out.format("%-10s | %-30s | %-10s | %-8s | %-10s", car.supplierName(),
            car.description(), car.sippCode(), car.rentalCost(), car.fuelPolicy());
            System.out.println();
        }
    }
}
