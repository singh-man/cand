![Static Badge](https://img.shields.io/badge/github-Jeffs_Car_Rental_repo-blue?logo=github)
![Build](https://img.shields.io/badge/build-passing-brightgreen)
![Coverage](https://img.shields.io/badge/coverage-90%25-brightgreen)![Coverage](https://img.shields.io/badge/Lines_of_code-274-yellow)


[![LinkedIn](https://img.shields.io/badge/LinkedIn-%230077B5.svg?logo=linkedin&logoColor=white)](https://www.linkedin.com/in/antonio-casado-15b21673/)
* * *
# Jeff's car Rental

This repository has the content of a POC task for SwissRE.
* * *
## Technology Stack

* **Java 21**
* **Junit 5.10.2**
* **Maven**
* **IntelliJ**
* * *
✅ TASK UNDERSTANDING
--------------------

We need a Java program that:

*   Takes multiple vehicle rental records
*   Calculates:
    *   Base cost per km
    *   Energy cost (fuel or electricity)
    *   Additional services (motorway, tunnel, congestion)
    *   Eco-bonus (for E-Vans)
*   Outputs a **receipt-like** summary:
    *   One section per vehicle
    *   All components
    *   Subtotals
    *   Grand total

* * *
## How the Key Business Rules Work

| Rule | Implementation |
|---|---|
| **Base cost** | Each `VehicleType` has its own per-km rate applied in the `calculateCost()` method |
| **Energy cost** | Fuel vehicles use CHF 1.95/liter; E-Van uses CHF 0.30/kWh — selected by vehicle type |
| **Motorway Vignette** | Flat CHF 9.00 added when the boolean flag is `true` |
| **Gubrist Tunnel** | First 2 passages charged at CHF 2.50 each; any beyond 2 are free (`Math.min(rentalRecord.getGubristPassages(), GUBRIST_FREE_AFTER_PAID)`) |
| **City Congestion** | CHF 1.00 × city km; only appears when `cityKm > 0` |
| **Eco-bonus** | Applies only to E-Vans with >80 km **and** consumption < 22 kWh/100 km → subtracts CHF 10.00. For Vehicle #1: $(20 / 95) \times 100 \approx 21.05$ kWh/100 km, which is below 22, so the bonus applies. |

* * * 
💡 CLASSES OVERVIEW (Approach Outline)
--------------------------------------

We'll design this using a clean object-oriented approach:

1.  **Enum**: `VehicleType` – with pricing constants
2.  **Class**: `RentalService` – encapsulates all cost logic
3.  **Class**: `RentalRecord` – stores input for a vehicle rental
4.  **Class**: `RentalCostSummary` – outputs the formatted receipt
5.  **Class**: `RentalFormatUtils` – formatting utilities for quantities and currency
6.  **Main class** to run the program with sample input

* * *
## Set up project

- Run this below command

```
mvn clean install
```
## Run Project

- Save the Java Code
- Compile

```bash
javac -d target/classes src/main/java/com/swissre/*.java
```
- Run it

```bash
java -cp target/classes com.swissre.JeffsCarRentalApp
```
## Run Tests Continuously

- Run this below command

```
mvn clean test
```
Every time you run this command, Surefire will:

*   Compile your test and main code
*   Run all tests
*   Fail the build if any test fails ✅

* * *

🧪 SETUP – Add JUnit 5 (Maven)
------------------------------

If you're using Maven, add this to your `pom.xml`:

```xml
<dependencies>
    <dependency>
        <groupId>org.junit.jupiter</groupId>
        <artifactId>junit-jupiter</artifactId>
        <version>5.10.2</version>
        <scope>test</scope>
    </dependency>
</dependencies>
```
🧪 HOW TO RUN TESTS
-------------------

If using IntelliJ:

*   Right-click `RentalServiceTest` → **Run**
*   Right-click `RentalCostSummaryTest` → **Run**

From CLI (Maven):

```bash
mvn test
```
* * *

✅ COVERAGE & CONFIDENCE
-----------------------

These tests cover:

*   Base and energy cost
*   Tunnel cost cap logic
*   Eco-bonus logic
*   Congestion zone handling
*   Combined services
*   Precision within ±0.01 CHF

* * *

## Continuous testing setup with Maven Surefire:

✅ STEP 1: ADD MAVEN SUREFIRE TO `pom.xml`
------------------------------------

Here’s how to configure the **Surefire plugin** to run all JUnit 5 tests during Maven's `test` phase.

```xml
<build>
    <plugins>
        <!-- Surefire Plugin for running tests -->
       <plugin>
          <groupId>org.apache.maven.plugins</groupId>
          <artifactId>maven-surefire-plugin</artifactId>
          <version>3.2.5</version>
       </plugin>
    </plugins>
</build>
```

* * *

# Add Jacoco 

✅ STEP 2: ADD JACOCO PLUGIN TO `pom.xml`
----------------------------------------

Insert this inside the `<plugins>` block in your `pom.xml`:

```xml
<plugin>
   <groupId>org.jacoco</groupId>
   <artifactId>jacoco-maven-plugin</artifactId>
   <version>0.8.13</version>
   <executions>
      <execution>
         <goals>
            <goal>prepare-agent</goal>
         </goals>
      </execution>
      <execution>
         <id>report</id>
         <phase>test</phase>
         <goals>
            <goal>report</goal>
         </goals>
      </execution>
      <execution>
         <id>jacoco-check</id>
         <goals>
            <goal>check</goal>
         </goals>
         <configuration>
            <rules>
               <rule>
                  <element>PACKAGE</element>
                  <limits>
                     <limit>
                        <counter>LINE</counter>
                        <value>COVEREDRATIO</value>
                        <minimum>0.9</minimum>
                     </limit>
                  </limits>
               </rule>
            </rules>
         </configuration>
      </execution>
   </executions>
</plugin>
```

📦 Place this **under the same `<plugins>`** tag where Maven Surefire is configured.

* * *

✅ STEP 3: RUN TESTS WITH COVERAGE
---------------------------------

To generate the coverage report, just run:

```bash
mvn clean verify
```

*   `prepare-agent`: Instruments code before tests
*   `report`: Generates reports **after** the `verify` phase

* * *

✅ STEP 4: FIND COVERAGE REPORTS
-------------------------------

After running, JaCoCo will generate the report here:

```
target/site/jacoco/index.html
```

You can open it in your browser to view **line-by-line** coverage like this:

```
file:///path/to/your/project/target/site/jacoco/index.html
```

* * *

📈 REPORT CONTENTS
------------------

You'll see:

*   **Overall coverage %** for classes, methods, lines
*   Drill-down into each class/method
*   Visual indication of covered and missed lines

* * *

✅ FAIL BUILD ON LOW COVERAGE (Policy)
------------------------------------------------

Want to **enforce** minimum coverage? Add this to the plugin:

```xml
<configuration>
   <rules>
      <rule>
         <element>PACKAGE</element>
         <limits>
            <limit>
                        <counter>LINE</counter>
                        <value>COVEREDRATIO</value>
                        <minimum>0.9</minimum>
                    </limit>
                </limits>
            </rule>
        </rules>
    </configuration>
```

Now a build will **fail if coverage is < 90%** ✅

* * *

🧪 QUICK TEST
-------------

You can verify it's working by running:

```bash
mvn clean verify
```

Then open:

```bash
target/site/jacoco/index.html
```

