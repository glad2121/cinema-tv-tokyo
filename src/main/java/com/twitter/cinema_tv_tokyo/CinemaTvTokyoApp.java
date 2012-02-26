package com.twitter.cinema_tv_tokyo;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.twitter.cinema_tv_tokyo.common.model.Page;
import com.twitter.cinema_tv_tokyo.common.model.Program;
import com.twitter.cinema_tv_tokyo.common.model.ProgramCriteria;
import com.twitter.cinema_tv_tokyo.common.util.DateUtils;
import com.twitter.cinema_tv_tokyo.search.ProgramSearchService;
import com.twitter.cinema_tv_tokyo.search.impl.ProgramSearchServiceImpl;
import com.twitter.cinema_tv_tokyo.tweet.ProgramTweetService;
import com.twitter.cinema_tv_tokyo.tweet.impl.ProgramTweetServiceImpl;
import com.twitter.cinema_tv_tokyo.tweet.impl.StdoutTweetWriter;

public class CinemaTvTokyoApp {

    private ProgramSearchService searchService;

    public CinemaTvTokyoApp(ProgramSearchService searchService) {
        this.searchService = searchService;
    }

    public void run(
            ProgramCriteria criteria, ProgramTweetService tweetService) {
        if (criteria == null) {
            // XXX 暫定仕様: 2日後の 18:00～24:00 に放映される番組をつぶやく。
            criteria = new ProgramCriteria();
            criteria.setOffset(0);
            Calendar calendar = Calendar.getInstance(DateUtils.JST);
            calendar.add(Calendar.DAY_OF_MONTH, 2);
            criteria.setDate(DateUtils.toYMD(calendar));
            criteria.setStartTime_ge("18:00");
            criteria.setFinishTime_le("24:00");
            criteria.setGcode_isNull(false);
        }
        Page<Program> page = searchService.findProgram(criteria);
        List<Program> list = new ArrayList<Program>();
        for (Program program : page.getContent()) {
            if (criteria.matches(program)) {
                list.add(program);
            }
        }
        tweetService.tweet(list);
    }

    public static void main(String[] args) {
        ProgramSearchService searchService =
                new ProgramSearchServiceImpl();
        ProgramTweetService tweetService =
                new ProgramTweetServiceImpl(new StdoutTweetWriter(), 1000);
        ProgramCriteria criteria = new ProgramCriteria();
        criteria.setStartTime_ge("18:00");
        new CinemaTvTokyoApp(searchService).run(criteria, tweetService);
    }

}
