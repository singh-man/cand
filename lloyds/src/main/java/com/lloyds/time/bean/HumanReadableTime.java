package com.lloyds.time.bean;

import com.lloyds.time.exception.HumanReadTimeException;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Stream;

@Component
public class HumanReadableTime implements IHumanReadableTime {
    // Generate Time in words.
    private String processTime(int hour, int minute) {
        String humanReadTime = "";
        hour = hour > 12 ? hour - 12 : hour;
        if (minute == 0)
            humanReadTime = clock[hour] + " o'clock";

        else if (minute == 15)
            humanReadTime = "Quarter past " + clock[hour].toLowerCase();

        else if (minute == 30)
            humanReadTime = "Half past " + clock[hour].toLowerCase();

        else if (minute == 45)
            humanReadTime = "Quarter to " + clock[(hour % 12) + 1].toLowerCase();

        else if (minute >= 1 && minute <= 29)
            humanReadTime = clock[minute] + " past " + clock[hour].toLowerCase();

        else if (minute > 30 && minute < 60)
            humanReadTime = clock[60 - minute] + " to " + clock[hour + 1].toLowerCase();

        return humanReadTime;
    }

    @Override
    public String getTime() {
        DateTimeFormatter dtfTime = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime localTime = LocalTime.ofInstant(Instant.now(), ZoneId.systemDefault());
        String currentTIme = dtfTime.format(localTime);
        String[] split = currentTIme.split(":");
        return processTime(Integer.parseInt(split[0]), Integer.parseInt(split[1]));
    }

    private void validate(int hour, int minute) {
        if (hour < 0 || hour > 24) throw new HumanReadTimeException("Invalid hour");
        if (minute < 0 || minute > 60) throw new HumanReadTimeException("Invalid minutes");
    }

    @Override
    public String getTime(int hour, int minute) {
        validate(hour, minute);
        return processTime(hour, minute);
    }

    /**
     * Chain of Responsibility
     */
    public String processTime_2(final int hour, int minute) {
        validate(hour, minute);
        BiFunction<Integer, Integer, Optional<String>> _0      = (h, m) -> m == 0 ? Optional.of(clock[h] + " o'clock") : Optional.empty();
        BiFunction<Integer, Integer, Optional<String>> _15     = (h, m) -> m == 15 ? Optional.of("Quarter past " + clock[h].toLowerCase()) : Optional.empty();
        BiFunction<Integer, Integer, Optional<String>> _30     = (h, m) -> m == 30 ? Optional.of("Half past " + clock[h].toLowerCase()) : Optional.empty();
        BiFunction<Integer, Integer, Optional<String>> _45     = (h, m) -> m == 45 ? Optional.of("Quarter to " + clock[(h % 12) + 1].toLowerCase()) : Optional.empty();
        BiFunction<Integer, Integer, Optional<String>> _1to29  = (h, m) -> m >= 1 && m <= 29 ? Optional.of(clock[m] + " past " + clock[h].toLowerCase()) : Optional.empty();
        BiFunction<Integer, Integer, Optional<String>> _31to59 = (h, m) -> m > 30 && m < 60 ? Optional.of(clock[60 - m] + " to " + clock[h + 1].toLowerCase()) : Optional.empty();

        var time = Stream.of(_0, _15, _30, _45, _1to29, _31to59)
                .map(e -> e.apply(hour > 12 ? hour - 12 : hour, minute))
                .filter(e -> e.isPresent())
                .map(e -> e.get())
                .findFirst();
        return time.get();
    }

    /**
     * Chain of Responsibility
     */
    public String processTime_3(final int hour, int minute) {
        validate(hour, minute);
        Function<Integer, Optional<String>> _0      = m -> m == 0 ? Optional.of(clock[hour] + " o'clock") : Optional.empty();
        Function<Integer, Optional<String>> _15     = m -> m == 15 ? Optional.of("Quarter past " + clock[hour].toLowerCase()) : Optional.empty();
        Function<Integer, Optional<String>> _30     = m -> m == 30 ? Optional.of("Half past " + clock[hour].toLowerCase()) : Optional.empty();
        Function<Integer, Optional<String>> _45     = m -> m == 45 ? Optional.of("Quarter to " + clock[(hour % 12) + 1].toLowerCase()) : Optional.empty();
        Function<Integer, Optional<String>> _1to29  = m -> m >= 1 && m <= 29 ? Optional.of(clock[m] + " past " + clock[hour].toLowerCase()) : Optional.empty();
        Function<Integer, Optional<String>> _31to59 = m -> m > 30 && m < 60 ? Optional.of(clock[60 - m] + " to " + clock[hour + 1].toLowerCase()) : Optional.empty();

        var time = Stream.of(_0, _15, _30, _45, _1to29, _31to59)
                .map(e -> e.apply(minute))
                .filter(e -> e.isPresent())
                .findFirst()
                .get();
        return time.get();
    }
}