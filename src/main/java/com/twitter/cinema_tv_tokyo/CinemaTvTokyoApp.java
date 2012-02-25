package com.twitter.cinema_tv_tokyo;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.twitter.cinema_tv_tokyo.common.model.Page;
import com.twitter.cinema_tv_tokyo.common.model.Program;
import com.twitter.cinema_tv_tokyo.common.model.ProgramCriteria;
import com.twitter.cinema_tv_tokyo.search.ProgramSearchService;
import com.twitter.cinema_tv_tokyo.search.impl.ProgramSearchServiceImpl;
import com.twitter.cinema_tv_tokyo.tweet.ProgramTweetService;
import com.twitter.cinema_tv_tokyo.tweet.impl.ProgramTweetServiceImpl;
import com.twitter.cinema_tv_tokyo.tweet.impl.StdoutTweetWriter;

public class CinemaTvTokyoApp {

    private ProgramSearchService searchService;

    private ProgramTweetService tweetService;

    public CinemaTvTokyoApp(
            ProgramSearchService searchService,
            ProgramTweetService tweetService) {
        this.searchService = searchService;
        this.tweetService = tweetService;
    }

    public void run(String[] args) {
        // XXX 暫定仕様: 2日後の 18:00～24:00 に放映される番組をつぶやく。
        ProgramCriteria criteria = new ProgramCriteria();
        criteria.setOffset(0);
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, 2);
        //criteria.setDate(calendar.getTime());
        Page<Program> page = searchService.findProgram(criteria);
        List<Program> list = new ArrayList<Program>();
        for (Program program : page.getContent()) {
            if (program.getStartTime().compareTo("18:00") < 0) continue;
            if (program.getFinishTime().compareTo("24:00") > 0) continue;
            if (program.getGcode() == null) continue;
            list.add(program);
        }
        tweetService.tweet(list);
    }

    public static void main(String[] args) {
        ProgramSearchService searchService =
                new ProgramSearchServiceImpl();
        ProgramTweetService tweetService =
                new ProgramTweetServiceImpl(new StdoutTweetWriter(), 1000);
        new CinemaTvTokyoApp(searchService, tweetService).run(args);
    }

}
