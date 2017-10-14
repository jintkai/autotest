package com.autotest.model;

import java.util.Date;

public class VariableResult {
    private Integer id;
    private Integer variableid;
    private Integer buildid;
    private String value;
    private String exception;
    private Date timestamp;
    private Date updatetime;
    public VariableResult(){};

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getVariableid() {
        return variableid;
    }

    public void setVariableid(Integer variableid) {
        this.variableid = variableid;
    }

    public Integer getBuildid() {
        return buildid;
    }

    public void setBuildid(Integer buildid) {
        this.buildid = buildid;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getException() {
        return exception;
    }

    public void setException(String exception) {
        this.exception = exception;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }
}
