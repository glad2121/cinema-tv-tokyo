package com.twitter.cinema_tv_tokyo.tweet.impl;

import com.twitter.cinema_tv_tokyo.tweet.TweetWriter;

public class StdoutTweetWriter implements TweetWriter {

    public void tweet(String tweet) {
        System.out.println(tweet);
    }

}
