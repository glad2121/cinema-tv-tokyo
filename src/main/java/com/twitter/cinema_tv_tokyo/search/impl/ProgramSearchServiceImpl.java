package com.twitter.cinema_tv_tokyo.search.impl;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.Segment;
import net.htmlparser.jericho.Source;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.twitter.cinema_tv_tokyo.common.model.Page;
import com.twitter.cinema_tv_tokyo.common.model.Program;
import com.twitter.cinema_tv_tokyo.common.model.ProgramCriteria;
import com.twitter.cinema_tv_tokyo.common.util.DateUtils;
import com.twitter.cinema_tv_tokyo.search.ProgramSearchService;

/**
 * {@link ProgramSearchService} の実装。
 * 
 * @author ITO Yoshiichi
 */
public class ProgramSearchServiceImpl implements ProgramSearchService {

    static final String BASE_URL =
            "http://tv.yahoo.co.jp/search/?g=06&oa=1&a=23&oc=%2B60";

    static final Logger logger =
            LoggerFactory.getLogger(ProgramSearchServiceImpl.class);

    public Page<Program> findProgram(ProgramCriteria criteria) {
        String url = BASE_URL;
        Integer offset = null;
        if (criteria != null) {
            offset = criteria.getOffset();
            StringBuilder sb = new StringBuilder();
            sb.append(url);
            String date = criteria.getDate();
            if (date != null) {
                sb.append("&d=").append(StringUtils.join(date.split("-")));
            }
            url = sb.toString();
        }
        if (offset == null) {
            return doFindAll(url);
        } else {
            return doFind(url, offset);
        }
    }

    Page<Program> doFindAll(String url) {
        List<Program> list = new ArrayList<Program>();
        Page<Program> page = doFind(url, null);
        list.addAll(page.getContent());
        while (list.size() < page.getTotalCount()) {
            page = doFind(url, list.size());
            list.addAll(page.getContent());
        }
        return new Page<Program>(list.size(), 0, list);
    }

    Page<Program> doFind(String url, Integer offset) {
        if (offset != null) {
            url += "&s=" + (offset + 1);
        }
        logger.debug(url);
        try {
            return parse(new URL(url));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    Page<Program> parse(URL url) throws IOException {
        return parse(new Source(url));
    }

    Page<Program> parse(Source source) throws IOException {
        Element main = source.getElementById("main");
        
        Element number = main.getFirstElementByClass("search_number");
        int totalCount = getTotalCount(number);
        logger.debug("totalCount: {}", totalCount);
        int offset = getOffset(number);
        logger.debug("offset: {}", offset);
        
        Calendar today = DateUtils.getToday(DateUtils.JST);
        
        List<Element> elements = main.getFirstElementByClass("programlist")
                .getAllElements("li");
        List<Program> list = new ArrayList<Program>();
        for (Element element : elements) {
            list.add(toProgram(element, today));
        }
        
        return new Page<Program>(totalCount, offset, list);
    }

    int getTotalCount(Element number) {
        Segment content = number.getAllElements("em").get(0).getContent();
        return Integer.parseInt(content.toString());
    }

    int getOffset(Element number) {
        Segment content = number.getAllElements("em").get(1).getContent();
        return Integer.parseInt(content.toString().split("\\D")[0]) - 1;
    }

    Program toProgram(Element element, Calendar today) {
        Program program = new Program();
        Element left = element.getFirstElementByClass("leftarea");
        String date = getDate(left, today);
        program.setDate(date);
        logger.debug("date: {}", date);
        
        String[] time = getTime(left);
        program.setStartTime(time[0]);
        program.setFinishTime(time[1]);
        logger.debug("time: {}-{}", time[0], time[1]);
        
        String gcode = getGcode(left);
        program.setGcode(gcode);
        logger.debug("gcode: {}", gcode);
        
        Element right = element.getFirstElementByClass("rightarea");
        String title = getTitle(right);
        program.setTitle(title);
        logger.debug("title: {}", title);
        
        String[] channel = getChannel(right);
        program.setChannel(channel[0]);
        logger.debug("channel: {}", channel[0]);
        program.setMedium(channel[1]);
        logger.debug("medium: {}", channel[1]);
        
        String description = getDescription(right);
        program.setDescription(description);
        logger.debug("description: {}", description);
        
        logger.debug("program: {}", program);
        return program;
    }

    /**
     * 日付を返す。
     * 
     * @return "YYYY-MM-DD"
     */
    String getDate(Element left, Calendar today) {
        String s = left.getFirstElement("em").getContent().toString();
        String[] a = s.split("/");
        int year = DateUtils.getYear(today);
        int month = Integer.parseInt(a[0]);
        int day = Integer.parseInt(a[1]);
        if (month < DateUtils.getMonth(today)) {
            ++year;
        }
        return String.format("%04d-%02d-%02d", year, month, day);
    }

    /**
     * 時刻を返す。
     * 
     * @return ["hh:mm", "hh:mm"]
     */
    String[] getTime(Element left) {
        String[] a = left.getAllElements("em").get(1)
                .getContent().toString().split("[^\\d:]");
        for (int i = 0; i < 2; ++i) {
            if (a[i].length() == 4) {
                a[i] = '0' + a[i];
            }
        }
        return a;
    }

    String getGcode(Element left) {
        Element gcode = left.getFirstElement("id", "gcode", true);
        return (gcode == null) ? null : gcode.getAttributeValue("value");
    }

    String getTitle(Element right) {
        return right.getFirstElement("a").getContent().toString();
    }

    String[] getChannel(Element right) {
        String s = right.getFirstElementByClass("pr35").getContent().toString();
        int index = s.lastIndexOf('（');
        String channel = s.substring(0, index);
        String medium = s.substring(index + 1, s.length() - 1);
        return new String[] {channel, medium};
    }

    String getDescription(Element right) {
        return right.getAllElements("p")
                .get(2).getContent().toString()
                .replaceAll("&lt;", "<")
                .replaceAll("&gt;", ">")
                .replaceAll("&amp;", "&");
    }

}
