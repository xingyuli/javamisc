package org.swordess.toy.javamisc.joda;

import org.joda.time.DateTime;
import org.joda.time.DateTimeUtils;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created by liuuyxin on 15-1-20.
 */
public class DateUtilsTest {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormat.forPattern("yyyyMMdd HH:mm:ss");

    @BeforeClass
    public static void beforeClass() {
        DateTimeUtils.setCurrentMillisFixed(DateTime.parse("20150120 11:20:30", DATE_TIME_FORMATTER).getMillis());
    }

    @Test
    public void testNow() {
        verifyOutput("20150120 11:20:30", DateUtils.now());
    }

    @Test
    public void testGetRemindDate() {
        verifyOutput("20150123 10:00:00", DateUtils.getRemindDate(DateUtils.now().toDate(), 3));
    }

    @Test
    public void testFirstDayOfCurrentMonth() {
        verifyOutput("20150101 00:00:00", DateUtils.firstDayOfCurrentMonth());
    }

    @Test
    public void testLastDayOfCurrentMonth() {
        verifyOutput("20150131 23:59:59", DateUtils.lastDayOfCurrentMonth());
    }

    @Test
    public void testRightBeforeToday() {
        verifyOutput("20150119 23:59:59", DateUtils.rightBeforeToday());
    }

    @Test
    public void testRightAfterToday() {
        verifyOutput("20150121 00:00:00", DateUtils.rightAfterToday());
    }

    @Test
    public void testRightBeforeTomorrow() {
        verifyOutput("20150120 23:59:59", DateUtils.rightBeforeTomorrow());
    }

    @Test
    public void testRightAfterTomorrow() {
        verifyOutput("20150122 00:00:00", DateUtils.rightAfterTomorrow());
    }

    private void verifyOutput(String expected, DateTime actual) {
        assertThat(actual.toString(DATE_TIME_FORMATTER), is(expected));
    }

}
