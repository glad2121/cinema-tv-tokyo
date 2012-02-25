package com.twitter.cinema_tv_tokyo.tweet.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

import com.twitter.cinema_tv_tokyo.common.exception.CinemaTvTokyoException;
import com.twitter.cinema_tv_tokyo.tweet.TweetWriter;

public class TwitterTweetWriter implements TweetWriter {

    static final Logger logger =
            LoggerFactory.getLogger(TwitterTweetWriter.class);

    private Twitter twitter = new TwitterFactory().getInstance();

    public void tweet(String tweet) {
        try {
            Status status = twitter.updateStatus(tweet);
            if (logger.isInfoEnabled()) {
                logger.info(status.getUser().getName()
                        + ": " + status.getText());
            }
        } catch (TwitterException e) {
            String message = "can't tweet: " + tweet;
            logger.error(message, e);
            throw new CinemaTvTokyoException(message, e);
        }
    }

    public static void main(String[] args) {
        new TwitterTweetWriter().tweet("Test from twitter4j.");
    }

}
