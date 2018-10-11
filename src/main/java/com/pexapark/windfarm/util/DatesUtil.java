package com.pexapark.windfarm.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

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
}
