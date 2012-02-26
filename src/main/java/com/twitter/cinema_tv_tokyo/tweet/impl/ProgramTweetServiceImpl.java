package com.twitter.cinema_tv_tokyo.tweet.impl;

import java.util.Calendar;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.twitter.cinema_tv_tokyo.common.model.Program;
import com.twitter.cinema_tv_tokyo.common.util.DateUtils;
import com.twitter.cinema_tv_tokyo.tweet.ProgramTweetService;
import com.twitter.cinema_tv_tokyo.tweet.TweetWriter;

public class ProgramTweetServiceImpl implements ProgramTweetService {

    private TweetWriter writer;

    private long interval;

    public ProgramTweetServiceImpl(TweetWriter writer, long interval) {
        this.writer = writer;
        this.interval = interval;
    }

    public void tweet(List<Program> programs) {
        Calendar today = DateUtils.getToday(DateUtils.JST);
        boolean delay = false;
        for (Program program : programs) {
            if (interval > 0) {
                if (delay) {
                    try {
                        Thread.sleep(interval);
                    } catch (InterruptedException e) {
                        break;
                    }
                }
                delay = true;
            }
            writer.tweet(toTweet(program, today));
        }
    }

    static final char[] DAY_OF_WEEK = "月火水木金土日".toCharArray();

    String toTweet(Program program, Calendar today) {
        StringBuilder sb = new StringBuilder();
        String date = program.getDate();
        Calendar calendar = DateUtils.parseDate(date, DateUtils.JST);
        int days = DateUtils.getDays(today, calendar);
        if (days == 0) {
            sb.append("【本日】, ");
        } else if (days < 7) {
            sb.append("【今週】, ");
        } else if (days < 14) {
            sb.append("【来週】, ");
        } else {
            sb.append(", ");
        }
        sb.append(date).append(", ");
        int dayOfWeek = DateUtils.getDayOfWeek(calendar);
        sb.append("(").append(DAY_OF_WEEK[dayOfWeek - 1]).append("), ");
        sb.append(program.getStartTime()).append("/");
        sb.append(program.getFinishTime()).append(", ");
        sb.append(StringUtils.defaultString(program.getGcode())).append(", ");
        sb.append(program.getChannel()).append(", ");
        sb.append(program.getTitle()).append(", ");
        sb.append(program.getDescription());
        if (sb.length() > 140) {
            sb.setLength(137);
            sb.append("...");
        }
        return sb.toString();
    }

}
