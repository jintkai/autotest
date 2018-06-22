package com.autotest.model;

import java.sql.Time;
import java.util.Date;

public class Suit {
    private Integer suitid;

    private String suitname;

    private Integer lastbuildid;

    private Integer status;

    private Date timestamp;

    private boolean email;

    private Time cycletime;

    public boolean isEmail() {
        return email;
    }

    public void setEmail(boolean email) {
        this.email = email;
    }

    public Time getCycletime() {
        return cycletime;
    }

    public void setCycletime(Time time) {
        this.cycletime = time;
    }
    public Integer getSuitid() {
        return suitid;
    }

    public void setSuitid(Integer suitid) {
        this.suitid = suitid;
    }

    public String getSuitname() {
        return suitname;
    }

    public void setSuitname(String suitname) {
        this.suitname = suitname == null ? null : suitname.trim();
    }

    public Integer getLastbuildid() {
        return lastbuildid;
    }

    public void setLastbuildid(Integer lastbuildid) {
        this.lastbuildid = lastbuildid;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}