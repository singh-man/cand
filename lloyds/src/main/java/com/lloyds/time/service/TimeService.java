package com.lloyds.time.service;

import com.lloyds.time.bean.HumanReadableTime;
import com.lloyds.time.bean.IHumanReadableTime;
import com.lloyds.time.exception.HumanReadTimeException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class TimeService implements ITimeService {

    private IHumanReadableTime humanReadableTime;

    private Function<String, String> func;

    public TimeService(IHumanReadableTime humanReadableTime,
                       // A chained func bean injected
                       @Qualifier("helloWorld") Function<String, String> func) {
        this.humanReadableTime = humanReadableTime;
        this.func = func;
    }

    @Override
    public String getTime(String time) {
        String apply = func.apply(time);
        System.out.println(apply);
        if (time == null) {
            return humanReadableTime.getTime();
        } else {
            int hour = 0, minute = 0;
            try {
                String[] split = time.split(":");
                if (split.length != 2) throw new NumberFormatException();
                hour = Integer.parseInt(split[0]);
                minute = Integer.parseInt(split[1]);
            } catch (NumberFormatException e) {
                throw new HumanReadTimeException("Supplied time must be in format hh:mm and only positive values");
            }
            return humanReadableTime.getTime(hour, minute);
        }
    }

    public static void main(String[] args) {
        TimeService timeService = new TimeService(null, null);
        timeService.humanReadableTime = new HumanReadableTime();
        System.out.println(timeService.getTime(args.length == 0 ? null : args[0]));
    }
}
