package com.minewhale.grabber.grabbercore.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateUtils {
    public final static DateTimeFormatter yyyy_MM_dd = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    public static String getDateStr(LocalDateTime time) {
        return time.format(yyyy_MM_dd);
    }
}
