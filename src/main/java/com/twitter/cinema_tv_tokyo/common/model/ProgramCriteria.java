package com.twitter.cinema_tv_tokyo.common.model;

public class ProgramCriteria {

    private Integer offset;

    public ProgramCriteria() {
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName()
                + " {offset=" + offset
                + "}";
    }

}
