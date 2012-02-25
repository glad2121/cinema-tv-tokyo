package com.twitter.cinema_tv_tokyo.tweet;

import java.util.List;

import com.twitter.cinema_tv_tokyo.common.model.Program;

public interface ProgramTweetService {

    void tweet(List<Program> programs);

}
