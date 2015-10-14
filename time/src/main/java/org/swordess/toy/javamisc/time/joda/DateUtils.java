package org.swordess.toy.javamisc.time.joda;

import org.joda.time.DateTime;
import org.joda.time.MutableDateTime;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class DateUtils {

    public static final String DATE_INPUT_MASK = "yyyy-mm-dd";
    public static final String DATE_INPUT_MASK_LONG = "yyyy-mm-dd hh:mm:ss";
    public static final String DATE_PATTERN = "yyyy-MM-dd";
    public static final String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat(DATE_PATTERN);
    public static final SimpleDateFormat DATE_TIME_FORMAT = new SimpleDateFormat(DATE_TIME_PATTERN);
    public static final Map<String, String> PERIOD_MAP = new HashMap<String, String>();

    static {
        //TimeZone.setDefault(TimeZone.getTimeZone("GMT+8"));
        PERIOD_MAP.put("daily", "%Y-%m-%d");
        PERIOD_MAP.put("weekly", "%Y-%u");
        PERIOD_MAP.put("monthly", "%Y-%m");
        PERIOD_MAP.put("yearly", "%Y");
    }

    public static void init() {
    }

    public static DateTime now() {
        return DateTime.now();
    }

    public static DateTime getRemindDate(Date date, int after) {
        MutableDateTime mdt = new MutableDateTime(date);
        mdt.addDays(after);
        mdt.setTime(10, 0, 0, 0);
        return mdt.toDateTime();
    }

    public static DateTime firstDayOfCurrentMonth() {
        MutableDateTime mdt = MutableDateTime.now();
        mdt.setDayOfMonth(1);
        mdt.setMillisOfDay(0);
        return mdt.toDateTime();
    }

    public static DateTime lastDayOfCurrentMonth() {
        MutableDateTime mdt = MutableDateTime.now();
        mdt.setDayOfMonth(mdt.dayOfMonth().getMaximumValue());
        mdt.setMillisOfDay(mdt.millisOfDay().getMaximumValue());
        return mdt.toDateTime();
    }

    public static DateTime rightBeforeToday() {
        return rightBefore(today()).toDateTime();
    }

    public static DateTime rightAfterToday() {
        return rightAfter(today()).toDateTime();
    }

    public static DateTime rightBeforeTomorrow() {
        return rightBefore(tomorrow()).toDateTime();
    }

    public static DateTime rightAfterTomorrow() {
        return rightAfter(tomorrow()).toDateTime();
    }

    private static MutableDateTime today() {
        return MutableDateTime.now();
    }

    private static MutableDateTime tomorrow() {
        MutableDateTime mdt = MutableDateTime.now();
        mdt.addDays(1);
        return mdt;
    }

    private static MutableDateTime rightBefore(MutableDateTime mdt) {
        mdt.addDays(-1);
        mdt.setMillisOfDay(mdt.millisOfDay().getMaximumValue());
        return mdt;
    }

    private static MutableDateTime rightAfter(MutableDateTime mdt) {
        mdt.addDays(1);
        mdt.setMillisOfDay(0);
        return mdt;
    }

}
