package com.pexapark.windfarm.util;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class DatesUtil {

    /**
     * Returns date id for today. Example 20181011.
     * Has to be generated on every call because actual date may change.
     *
     * @return a number that represents today's date Id
     */
    public static int getTodaysDateId() {
        return Integer.parseInt(LocalDate.now().format(DateTimeFormatter.BASIC_ISO_DATE));
    }

    /**
     * Depending on dateId daily capacity of the farm may differ.
     * In the day when daylight saving time is starts - a day has 25hours, when ends - 23 hours.
     *
     * @param dateId
     * @param hourlyCapacity
     * @return
     */
    public BigDecimal getDailyCapacity(final Integer dateId, final BigDecimal hourlyCapacity) {
        long hoursInDay = getHoursInDay(dateId);
        return hourlyCapacity.multiply(new BigDecimal(hoursInDay));
    }

    public static long getHoursInDay(final Integer dateId) {
        LocalDate localDate = LocalDate.parse(dateId.toString(), DateTimeFormatter.BASIC_ISO_DATE);
        return ChronoUnit.HOURS.between(
                localDate.atStartOfDay(ZoneId.systemDefault()),
                localDate.plusDays(1).atStartOfDay(ZoneId.systemDefault()));
    }
}
