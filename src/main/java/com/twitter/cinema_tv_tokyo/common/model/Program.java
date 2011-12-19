package com.twitter.cinema_tv_tokyo.common.model;

/**
 * 番組。
 * 
 * @author ITO Yoshiichi
 */
public class Program {

    /**
     * 日付。
     */
    private String date;

    /**
     * 開始時刻。
     */
    private String startTime;

    /**
     * 終了時刻。
     */
    private String finishTime;

    /**
     * G コード。
     */
    private String gcode;

    /**
     * タイトル。
     */
    private String title;

    /**
     * 放送局。
     */
    private String channel;

    /**
     * 媒体。
     */
    private String medium;

    /**
     * 説明。
     */
    private String description;

    public Program() {
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(String finishTime) {
        this.finishTime = finishTime;
    }

    public String getGcode() {
        return gcode;
    }

    public void setGcode(String gcode) {
        this.gcode = gcode;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getMedium() {
        return medium;
    }

    public void setMedium(String medium) {
        this.medium = medium;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName()
                + " {date=" + date
                + ", startTime=" + startTime
                + ", finishTime=" + finishTime
                + ", gcode=" + gcode
                + ", title=" + title
                + ", channel=" + channel
                + ", medium=" + medium
                + ", description=" + description
                + "}";
    }

}
