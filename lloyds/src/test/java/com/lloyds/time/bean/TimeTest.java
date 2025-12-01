package com.lloyds.time.bean;

import com.lloyds.time.exception.HumanReadTimeException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TimeTest {

    private IHumanReadableTime time;

    @BeforeEach
    public void setUp() {
        time = new HumanReadableTime();
    }

    @Test
    public void testGivenExample() {
        System.out.println(time.getTime());
        Assertions.assertEquals("One o'clock", time.getTime(1, 0));
        Assertions.assertEquals("Two o'clock", time.getTime(2, 0));
        Assertions.assertEquals("One o'clock", time.getTime(13, 0));
        Assertions.assertEquals("Five past one", time.getTime(13, 5));
        Assertions.assertEquals("Ten past one", time.getTime(13, 10));
        Assertions.assertEquals("Twenty five past one", time.getTime(13, 25));
        Assertions.assertEquals("Half past one", time.getTime(13, 30));
        Assertions.assertEquals("Twenty five to two", time.getTime(13, 35));
        Assertions.assertEquals("Five to two", time.getTime(13, 55));
        Assertions.assertEquals("Half past four", time.getTime(16, 30));
        Assertions.assertEquals("Twelve o'clock", time.getTime(12, 00));
        Assertions.assertEquals("Twelve past twelve", time.getTime(12, 12));
    }

    @Test
    public void testZeroTime() {
        Assertions.assertEquals("Zero o'clock", time.getTime(0, 0));
        Assertions.assertEquals("Zero o'clock", time.getTime(00, 00));
        Assertions.assertEquals("Quarter past zero", time.getTime(00, 15));
    }

    @Test
    public void testWrongTime() {
        exception(-1, 10);
        exception(1, -1);
        exception(-1, -1);

        exception(120, 23);
        exception(12, 230);
        Assertions.assertEquals("Quarter past zero", time.getTime(00, 15));
    }

    @Test
    public void testProcessTime_2() {
        exception(-1, 10);
        exception(1, -1);
        exception(-1, -1);
        exception(120, 23);
        exception(12, 230);
        Assertions.assertEquals("Quarter past zero", new HumanReadableTime().processTime_2(00, 15));
    }

    @Test
    public void testProcessTime_3() {
        var time = new HumanReadableTime();
        exception(-1, 10);
        exception(1, -1);
        exception(-1, -1);
        exception(120, 23);
        exception(12, 230);
        Assertions.assertEquals("Quarter past zero", time.processTime_3(00, 15));
    }

    private HumanReadTimeException exception(int hour, int minute) {
        var time = new HumanReadableTime();
        return Assertions.assertThrows(HumanReadTimeException.class, () -> time.processTime_3(-1, 10));
    }
}