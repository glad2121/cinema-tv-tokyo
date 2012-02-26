package com.twitter.cinema_tv_tokyo.common.model;

/**
 * 番組の検索条件。
 * 
 * @author ITO Yoshiichi
 */
public class ProgramCriteria {

    private Integer offset;

    private String date;

    private String startTime_ge;

    private String finishTime_le;

    private Boolean gcode_isNull;

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

    public String getStartTime_ge() {
        return startTime_ge;
    }

    public void setStartTime_ge(String startTime_ge) {
        this.startTime_ge = startTime_ge;
    }

    public String getFinishTime_le() {
        return finishTime_le;
    }

    public void setFinishTime_le(String finishTime_le) {
        this.finishTime_le = finishTime_le;
    }

    public Boolean getGcode_isNull() {
        return gcode_isNull;
    }

    public void setGcode_isNull(Boolean gcode_isNull) {
        this.gcode_isNull = gcode_isNull;
    }

    public boolean matches(Program program) {
        if (date != null && !program.getDate().equals(date)) {
            return false;
        }
        if (startTime_ge != null
                && program.getStartTime().compareTo(startTime_ge) < 0) {
            return false;
        }
        if (finishTime_le != null
                && program.getFinishTime().compareTo(finishTime_le) > 0) {
            return false;
        }
        if (gcode_isNull != null
                && (program.getGcode() == null) != gcode_isNull) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName()
                + " {offset=" + offset
                + ", date=" + date
                + ", startTime_ge=" + startTime_ge
                + ", finishTime_le=" + finishTime_le
                + ", gcode_isNull=" + gcode_isNull
                + "}";
    }

}
