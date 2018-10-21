package com.pexapark.windfarm.util;

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
     * In the day when daylight saving time starts - a day has 23hours, when ends - 25 hours.
     *
     * @param dateId
     * @param timezone
     * @return hours in day. Might be either 23, 24 or 25
     */
    public static long getHoursInDay(final Integer dateId, final String timezone) {
        final LocalDate localDate = LocalDate.parse(dateId.toString(), DateTimeFormatter.BASIC_ISO_DATE);
        final ZoneId zoneId = ZoneId.of(timezone);
        return ChronoUnit.HOURS.between(
                localDate.atStartOfDay(zoneId),
                localDate.plusDays(1).atStartOfDay(zoneId));
    }
}
