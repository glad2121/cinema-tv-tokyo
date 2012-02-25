package com.twitter.cinema_tv_tokyo.common.model;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 番組の検索条件。
 * 
 * @author ITO Yoshiichi
 */
public class ProgramCriteria {

    private Integer offset;

    private String date;

    public ProgramCriteria() {
    }

    public ProgramCriteria(ProgramCriteria other) {
        if (other == null) {
            return;
        }
        this.offset = other.offset;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setDate(Date date) {
        setDate(new SimpleDateFormat("yyyy-MM-dd").format(date));
    }

    @Override
    public String toString() {
        return getClass().getSimpleName()
                + " {offset=" + offset
                + ", date=" + date
                + "}";
    }

}
