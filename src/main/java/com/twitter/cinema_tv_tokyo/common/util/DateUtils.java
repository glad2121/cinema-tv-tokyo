package com.twitter.cinema_tv_tokyo.common.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

public class DateUtils {

    public static final TimeZone JST = TimeZone.getTimeZone("Asia/Tokyo");

    public static Calendar getToday(TimeZone tz) {
        Calendar calendar = new GregorianCalendar(tz);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        calendar.clear();
        calendar.set(year, month, day);
        return calendar;
    }

    public static String toYMD(Date date, TimeZone tz) {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        format.setCalendar(new GregorianCalendar(tz));
        return format.format(date);
    }

    public static String toYMD(Calendar calendar) {
        return toYMD(calendar.getTime(), calendar.getTimeZone());
    }

    public static Calendar parseDate(String ymd, TimeZone tz) {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        format.setCalendar(new GregorianCalendar(tz));
        try {
            format.parse(ymd);
        } catch (ParseException e) {
            throw new IllegalArgumentException(ymd, e);
        }
        return format.getCalendar();
    }

    static final int MILLIS_IN_DAY = 24 * 60 * 60 * 1000;

    public static int getDays(String from, String to) {
        TimeZone tz = TimeZone.getDefault();
        return getDays(parseDate(from, tz), parseDate(to, tz));
    }

    public static int getDays(Calendar from, Calendar to) {
        return (int) (getMillis(from, to) / MILLIS_IN_DAY);
    }

    public static long getMillis(Calendar from, Calendar to) {
        return to.getTimeInMillis() - from.getTimeInMillis();
    }

    public static int getDayOfWeek(String ymd) {
        return getDayOfWeek(parseDate(ymd, TimeZone.getDefault()));
    }

    public static int getDayOfWeek(Calendar calendar) {
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        if (dayOfWeek == 1) {
            return 7;
        } else {
            return dayOfWeek - 1;
        }
    }

}
