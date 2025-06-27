package com.lloyds.time.service;

import com.lloyds.time.bean.IHumanReadableTime;
import com.lloyds.time.exception.HumanReadTimeException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TimeServiceTest {

    TimeService timeService;
    IHumanReadableTime humanReadableTime;

    @BeforeEach
    public void setUp() {
        timeService = new TimeService();
        humanReadableTime = Mockito.mock(IHumanReadableTime.class);
        timeService.setHumanReadableTime(humanReadableTime);
    }

    @Test
    public void testTimeSuccess() {
        Mockito.when(humanReadableTime.getTime()).thenReturn("Twelve o'clock");
        assertEquals("Twelve o'clock", timeService.getTime(null));
        Mockito.when(humanReadableTime.getTime(16, 30)).thenReturn("Half past four");
        assertEquals("Half past four", timeService.getTime("16:30"));
        Mockito.when(humanReadableTime.getTime(4, 30)).thenReturn("Half past four");
        assertEquals("Half past four", timeService.getTime("4:30"));
    }

    @Test
    public void testTimeFailure() {
//        Mockito.when(this.timeService.getTime(Mockito.anyString())).thenReturn(new HumanReadableTime().getTime());
        Assertions.assertThrows(HumanReadTimeException.class, () -> timeService.getTime("-1-1"));
        Assertions.assertThrows(HumanReadTimeException.class, () -> timeService.getTime("430"));
        Assertions.assertThrows(HumanReadTimeException.class, () -> timeService.getTime("43:24:23"));
    }
}
