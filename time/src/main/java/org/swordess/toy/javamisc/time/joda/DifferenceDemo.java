package org.swordess.toy.javamisc.time.joda;

import org.joda.time.Days;
import org.joda.time.Period;
import org.joda.time.PeriodType;
import org.joda.time.ReadableInstant;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.PeriodFormat;

public class DifferenceDemo {

    private static final DateTimeFormatter FORMATTER = DateTimeFormat.forPattern("yyyyMMdd HH:mm:ss");

    public static Days differenceOfDays(ReadableInstant start, ReadableInstant end) {
        return Days.daysBetween(start, end);
    }

    public static Period differenceOfPeriodType(ReadableInstant start, ReadableInstant end, PeriodType type) {
        return new Period(start.getMillis(), end.getMillis(), type);
    }

    public static void main(String[] args) {
        System.out.println(differenceOfDays(FORMATTER.parseDateTime("20150109 10:00:00"), FORMATTER.parseDateTime("20150113 09:00:00")).getDays());
        System.out.println(differenceOfDays(FORMATTER.parseDateTime("20150109 10:00:00"), FORMATTER.parseDateTime("20150113 11:00:00")).getDays());

        Period p = differenceOfPeriodType(FORMATTER.parseDateTime("20000101 10:00:00"), FORMATTER.parseDateTime("20150310 11:11:11"), PeriodType.yearMonthDayTime());
        System.out.println(PeriodFormat.wordBased().print(p));
    }

}
