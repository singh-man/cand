package com.lloyds.time.service;

import com.lloyds.time.bean.HumanReadableTime;
import com.lloyds.time.bean.IHumanReadableTime;
import com.lloyds.time.exception.HumanReadTimeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TimeService implements ITimeService {

    private IHumanReadableTime humanReadableTime;

    @Override
    public String getTime(String time) {
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

    @Autowired
    public void setHumanReadableTime(IHumanReadableTime humanReadableTime) {
        this.humanReadableTime = humanReadableTime;
    }

    public static void main(String[] args) {
        TimeService timeService = new TimeService();
        timeService.humanReadableTime = new HumanReadableTime();
        System.out.println(timeService.getTime(args.length == 0 ? null : args[0]));
    }
}
