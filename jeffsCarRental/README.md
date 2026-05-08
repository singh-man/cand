# Jeff's Car Rental - Vehicle Rental Cost Summary

A simple, SOLID, Java 21 calculation module that computes costs for vehicle rentals and produces a beautifully formatted console receipt.

## Architecture

This project is designed following SOLID principles:
- **Single Responsibility Principle (SRP)**: Each cost type (Base Distance, Energy, Gubrist Fee, etc.) has its own decoupled `CostCalculator`. Formatting is handled strictly by `ConsoleReceiptFormatter`.
- **Open/Closed Principle (OCP)**: Adding new pricing factors or taxes simply means adding a new class implementing `CostCalculator` and passing it to the `RentalCostCalculator` orchestrator, without modifying existing calculation logic.

## Prerequisites
- Java SE 21 (or newer)
- Maven 3.6+

## How to Build and Run Tests
This project relies on standard Maven lifecycle goals.

**To compile and run unit tests:**
```bash
mvn clean test
```

**To generate Test Coverage Reports (JaCoCo):**
```bash
mvn clean test jacoco:report
```
The test coverage report will be available at `target/site/jacoco/index.html`.

## How to Run the Demonstration Target
To calculate the cost summaries using the exact input examples detailed in the assignment PDF, you can execute the `JeffsCarRentalApp` main class directly via Maven:

```bash
mvn compile exec:java -Dexec.mainClass="com.swissre.JeffsCarRentalApp"
```

This will print the full receipt, line by line, demonstrating sub-totaling, itemized cost presentation, and grand total aggregations.
