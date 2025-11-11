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
        Assertions.assertThrows(HumanReadTimeException.class, () -> time.getTime(-1, 10));
        Assertions.assertThrows(HumanReadTimeException.class, () -> time.getTime(1, -1));
        Assertions.assertThrows(HumanReadTimeException.class, () -> time.getTime(-1, -1));

        Assertions.assertThrows(HumanReadTimeException.class, () -> time.getTime(120, 23));
        Assertions.assertThrows(HumanReadTimeException.class, () -> time.getTime(12, 230));
        Assertions.assertEquals("Quarter past zero", time.getTime(00, 15));
    }

    @Test
    public void testProcessTime_2() {
        Assertions.assertThrows(HumanReadTimeException.class, () -> new HumanReadableTime().processTime_2(-1, 10));
        Assertions.assertThrows(HumanReadTimeException.class, () -> new HumanReadableTime().processTime_2(1, -1));
        Assertions.assertThrows(HumanReadTimeException.class, () -> new HumanReadableTime().processTime_2(-1, -1));

        Assertions.assertThrows(HumanReadTimeException.class, () -> new HumanReadableTime().processTime_2(120, 23));
        Assertions.assertThrows(HumanReadTimeException.class, () -> new HumanReadableTime().processTime_2(12, 230));
        Assertions.assertEquals("Quarter past zero", new HumanReadableTime().processTime_2(00, 15));
    }

    @Test
    public void testProcessTime_3() {
        var time = new HumanReadableTime();
        Assertions.assertThrows(HumanReadTimeException.class, () -> time.processTime_3(-1, 10));
        Assertions.assertThrows(HumanReadTimeException.class, () -> time.processTime_3(1, -1));
        Assertions.assertThrows(HumanReadTimeException.class, () -> time.processTime_3(-1, -1));

        Assertions.assertThrows(HumanReadTimeException.class, () -> time.processTime_3(120, 23));
        Assertions.assertThrows(HumanReadTimeException.class, () -> time.processTime_3(12, 230));
        Assertions.assertEquals("Quarter past zero", time.processTime_3(00, 15));
    }
}