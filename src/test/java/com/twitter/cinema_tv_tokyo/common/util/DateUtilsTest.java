package com.twitter.cinema_tv_tokyo.common.util;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.GregorianCalendar;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DateUtilsTest {

    static final Logger logger = LoggerFactory.getLogger(DateUtilsTest.class);

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    public void testToday() {
        Calendar today = DateUtils.getToday(DateUtils.JST);
        assertSame(GregorianCalendar.class, today.getClass());
        logger.debug(DateUtils.toYMD(today));
        assertEquals(0, today.get(Calendar.HOUR_OF_DAY));
        assertEquals(0, today.get(Calendar.MINUTE));
        assertEquals(0, today.get(Calendar.SECOND));
        assertEquals(0, today.get(Calendar.MILLISECOND));
    }

    @Test
    public void testToYMD() {
        Calendar calendar = new GregorianCalendar();
        calendar.set(2012, 0, 12);
        assertEquals("2012-01-12", DateUtils.toYMD(calendar));
    }

    @Test
    public void testParseDate() {
        Calendar calendar = DateUtils.parseDate("2012-02-23", DateUtils.JST);
        assertSame(GregorianCalendar.class, calendar.getClass());
        assertEquals(2012, calendar.get(Calendar.YEAR));
        assertEquals(   1, calendar.get(Calendar.MONTH));
        assertEquals(  23, calendar.get(Calendar.DAY_OF_MONTH));
        assertEquals(   0, calendar.get(Calendar.HOUR_OF_DAY));
        assertEquals(   0, calendar.get(Calendar.MINUTE));
        assertEquals(   0, calendar.get(Calendar.SECOND));
        assertEquals(   0, calendar.get(Calendar.MILLISECOND));
    }

    @Test
    public void testDays() {
        assertEquals(4, DateUtils.getDays("2012-02-26", "2012-03-01"));
    }

    @Test
    public void testDayOfWeek() {
        assertEquals(1, DateUtils.getDayOfWeek("2012-02-20"));
        assertEquals(6, DateUtils.getDayOfWeek("2012-02-25"));
        assertEquals(7, DateUtils.getDayOfWeek("2012-02-26"));
    }

}
