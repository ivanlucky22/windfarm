package com.pexapark.windfarm.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneOffset;

public class Utils {

    /**
     * Returns representation of hour in seconds.
     * Ex 1 am is 3600 seconds
     *
     * @param hours time in hours to be converted to seconds
     * @return
     */
    public static int getSecondsFromHour(final int hours) {
        LocalDateTime midnight = LocalDate.of(1970, Month.JANUARY, 1).atStartOfDay();
        long seconds = midnight.plusHours(hours).toEpochSecond(ZoneOffset.UTC);
        return (int) seconds;
    }
}