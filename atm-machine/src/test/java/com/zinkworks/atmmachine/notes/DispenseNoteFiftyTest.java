package com.zinkworks.atmmachine.notes;

import com.zinkworks.atmmachine.controller.dto.DispensedCashDTO;
import com.zinkworks.atmmachine.entity.ATM;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class DispenseNoteFiftyTest {

    private DispenseNoteFifty dispenser;

    @Before
    public void setUp() {
        dispenser = new DispenseNoteFifty();
    }

    @Test
    public void testByCOR() {
        ATM atm = new ATM(20, 30, 30, 100);
        DispenserResult dispenserResult = new DispenserResult(new DispensedCashDTO(), 250);
        DispenserResult dispense = dispenser.dispense(atm, dispenserResult);
        Assert.assertEquals(Integer.valueOf(5), dispense.getDispensedCashDTO().getNote50());
        Assert.assertEquals(Integer.valueOf(5), dispenserResult.getDispensedCashDTO().getNote50());
        Assert.assertEquals(Integer.valueOf(95), atm.getNote50());
    }

    @Test
    public void testByCOR_0_note() {
        ATM atm = new ATM(20, 30, 30, 0);
        DispenserResult dispenserResult = new DispenserResult(new DispensedCashDTO(), 250);
        Assert.assertEquals(Integer.valueOf(0), atm.getNote50());
        DispenserResult dispense = dispenser.dispense(atm, dispenserResult);
        Assert.assertEquals(Integer.valueOf(0), dispense.getDispensedCashDTO().getNote50());
        Assert.assertEquals(Integer.valueOf(0), dispenserResult.getDispensedCashDTO().getNote50());
        Assert.assertEquals(Integer.valueOf(0), atm.getNote50());
    }

    @Test
    public void testByCOR_1_note() {
        ATM atm = new ATM(20, 30, 30, 1);
        DispenserResult dispenserResult = new DispenserResult(new DispensedCashDTO(), 250);
        Assert.assertEquals(Integer.valueOf(1), atm.getNote50());
        DispenserResult dispense = dispenser.dispense(atm, dispenserResult);
        Assert.assertEquals(Integer.valueOf(1), dispense.getDispensedCashDTO().getNote50());
        Assert.assertEquals(Integer.valueOf(1), dispenserResult.getDispensedCashDTO().getNote50());
        Assert.assertEquals(Integer.valueOf(0), atm.getNote50());
    }

    @Test
    public void testByFunChaining() {
        ATM atm = new ATM(20, 30, 30, 100);
        DispenserResult dispenserResult = new DispenserResult(new DispensedCashDTO(), 250);
        DispenserResult_2 dispense = dispenser.dispense(new DispenserResult_2(atm, dispenserResult));
        Assert.assertEquals(Integer.valueOf(5), dispense.result().getDispensedCashDTO().getNote50());
        Assert.assertEquals(Integer.valueOf(5), dispenserResult.getDispensedCashDTO().getNote50());
        Assert.assertEquals(Integer.valueOf(95), atm.getNote50());
    }

    @Test
    public void testByFunChaining_0_note() {
        ATM atm = new ATM(20, 30, 30, 0);
        DispenserResult dispenserResult = new DispenserResult(new DispensedCashDTO(), 250);
        Assert.assertEquals(Integer.valueOf(0), atm.getNote50());
        DispenserResult_2 dispense = dispenser.dispense(new DispenserResult_2(atm, dispenserResult));
        Assert.assertEquals(Integer.valueOf(0), dispense.result().getDispensedCashDTO().getNote50());
        Assert.assertEquals(Integer.valueOf(0), dispenserResult.getDispensedCashDTO().getNote50());
        Assert.assertEquals(Integer.valueOf(0), atm.getNote50());
    }

    @Test
    public void testByFunChaining_1_note() {
        ATM atm = new ATM(20, 30, 30, 1);
        DispenserResult dispenserResult = new DispenserResult(new DispensedCashDTO(), 250);
        Assert.assertEquals(Integer.valueOf(1), atm.getNote50());
        DispenserResult_2 dispense = dispenser.dispense(new DispenserResult_2(atm, dispenserResult));
        Assert.assertEquals(Integer.valueOf(1), dispense.result().getDispensedCashDTO().getNote50());
        Assert.assertEquals(Integer.valueOf(1), dispenserResult.getDispensedCashDTO().getNote50());
        Assert.assertEquals(Integer.valueOf(0), atm.getNote50());
    }
}