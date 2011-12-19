package com.twitter.cinema_tv_tokyo.common.model;

import java.util.List;

/**
 * ページ。
 * 
 * @author ITO Yoshiichi
 */
public class Page<T> {

    private int totalCount;

    private int offset;

    private List<T> content;

    public Page(int totalCount, int offset, List<T> content) {
        this.totalCount = totalCount;
        this.offset = offset;
        this.content = content;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public int getOffset() {
        return offset;
    }

    public int getCount() {
        return getContent().size();
    }

    public List<T> getContent() {
        return content;
    }

    public T getContent(int index) {
        return getContent().get(index);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName()
                + " {totalCount=" + totalCount
                + ", offset=" + offset
                + ", content=" + content
                + "}";
    }

}
