package com.twitter.cinema_tv_tokyo.tweet.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.twitter.cinema_tv_tokyo.common.model.Program;
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
            writer.tweet(toTweet(program));
        }
    }

    String toTweet(Program program) {
        StringBuilder sb = new StringBuilder();
        sb.append(program.getDate()).append(", ");
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
