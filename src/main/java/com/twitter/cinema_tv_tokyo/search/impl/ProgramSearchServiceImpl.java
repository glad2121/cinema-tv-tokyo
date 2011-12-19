package com.twitter.cinema_tv_tokyo.search.impl;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.Segment;
import net.htmlparser.jericho.Source;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.twitter.cinema_tv_tokyo.common.model.Page;
import com.twitter.cinema_tv_tokyo.common.model.Program;
import com.twitter.cinema_tv_tokyo.common.model.ProgramCriteria;
import com.twitter.cinema_tv_tokyo.search.ProgramSearchService;

/**
 * 
 * 
 * @author ITO Yoshiichi
 */
public class ProgramSearchServiceImpl implements ProgramSearchService {

    static final Logger logger =
            LoggerFactory.getLogger(ProgramSearchServiceImpl.class);

    public Page<Program> findProgram(ProgramCriteria criteria) {
        if (criteria == null || criteria.getOffset() == null) {
            List<Program> list = doFindAll();
            return new Page<Program>(list.size(), 0, list);
        } else {
            return doFind(criteria.getOffset());
        }
    }

    List<Program> doFindAll() {
        List<Program> list = new ArrayList<Program>();
        Page<Program> page = doFind(null);
        list.addAll(page.getContent());
        while (list.size() < page.getTotalCount()) {
            page = doFind(list.size());
            list.addAll(page.getContent());
        }
        return list;
    }

    static final String BASE_URL =
            "http://tv.yahoo.co.jp/search/?g=06&oa=1&a=23";

    Page<Program> doFind(Integer offset) {
        String url = BASE_URL;
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
        Element result = main.getFirstElementByClass("search_result");
        int totalCount = getTotalCount(result);
        logger.debug("totalCount: {}", totalCount);
        
        Element numberLine = main.getFirstElementByClass("search_numberline");
        int offset = getOffset(numberLine);
        logger.debug("offset: {}", offset);
        
        List<Element> elements = main.getAllElementsByClass("program_bg");
        List<Program> list = new ArrayList<Program>();
        for (Element element : elements) {
            list.add(toProgram(element));
        }
        
        return new Page<Program>(totalCount, offset, list);
    }

    int getTotalCount(Element result) {
        Segment content = result.getAllElements("em").get(1).getContent();
        return Integer.parseInt(content.toString());
    }

    int getOffset(Element numberLine) {
        Segment content = numberLine.getAllElements("em").get(1).getContent();
        return Integer.parseInt(content.toString().split("\\D")[0]) - 1;
    }

    Program toProgram(Element element) {
        Program program = new Program();
        Element left = element.getFirstElementByClass("program_l");
        String date = getDate(left);
        program.setDate(date);
        logger.debug("date: {}", date);
        
        String[] time = getTime(left);
        program.setStartTime(time[0]);
        program.setFinishTime(time[1]);
        logger.debug("time: {}-{}", time[0], time[1]);
        
        String gcode = getGcode(left);
        program.setGcode(gcode);
        logger.debug("gcode: {}", gcode);
        
        Element right = element.getFirstElementByClass("program_r");
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

    String getDate(Element left) {
        return left.getFirstElement("span")
                .getContent().toString().replace('/', '-');
    }

    String[] getTime(Element left) {
        return left.getFirstElement("em")
                .getContent().toString().split("[^\\d:]");
    }

    String getGcode(Element left) {
        Element gcode = left.getFirstElement("id", "gcode", true);
        return (gcode == null) ? null : gcode.getAttributeValue("value");
    }

    String getTitle(Element right) {
        return right.getFirstElement("a")
                .getFirstElement("em").getContent().toString();
    }

    String[] getChannel(Element right) {
        Element p = right.getFirstElementByClass("pt5p");
        String channel = p.getFirstElement("em").getContent().toString();
        String s = p.getContent().toString();
        int index = s.lastIndexOf('（') + 1;
        String medium = s.substring(index, s.length() - 1);
        return new String[] {channel, medium};
    }

    String getDescription(Element right) {
        return right.getAllElements("p")
                .get(2).getContent().toString();
    }

}
