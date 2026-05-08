package com.swissre;

import java.util.ArrayList;
import java.util.List;

import static com.swissre.RentalFormatUtils.bd;

/**
 * JeffsCarRentalApp
 */
public class JeffsCarRentalApp {
    public static void main(String[] args) {

        List<RentalRecord> rentals = new ArrayList<>();
        rentals.add(new RentalRecord(
                VehicleType.E_VAN, 95, bd("20"), true, 3, 0));

        rentals.add(new RentalRecord(
                VehicleType.COMPACT_VAN, 40, bd("5"), false, 0, 0));

        rentals.add(new RentalRecord(
                VehicleType.LARGE_VAN, 180, bd("15"), false, 0, 30));

        RentalCostSummary.printSummary(rentals);
    }
}
