package com.autotest.model;

import java.util.Date;

public class SuitCase {
    private Integer id;

    private Integer suitid;

    private Integer caseid;

    private String casename;

    private String requesttype;

    private String requesturl;

    private String requestheader;

    private String requestbody;

    private String dependent;

    private String requestorder;

    private String assertexp;

    private Integer moduleid;

    private Integer disable;

    private Date timestamp;

    private Date updatetime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSuitid() {
        return suitid;
    }

    public void setSuitid(Integer suitid) {
        this.suitid = suitid;
    }

    public Integer getCaseid() {
        return caseid;
    }

    public void setCaseid(Integer caseid) {
        this.caseid = caseid;
    }

    public String getCasename() {
        return casename;
    }

    public void setCasename(String casename) {
        this.casename = casename == null ? null : casename.trim();
    }

    public String getRequesttype() {
        return requesttype;
    }

    public void setRequesttype(String requesttype) {
        this.requesttype = requesttype == null ? null : requesttype.trim();
    }

    public String getRequesturl() {
        return requesturl;
    }

    public void setRequesturl(String requesturl) {
        this.requesturl = requesturl == null ? null : requesturl.trim();
    }

    public String getRequestheader() {
        return requestheader;
    }

    public void setRequestheader(String requestheader) {
        this.requestheader = requestheader == null ? null : requestheader.trim();
    }

    public String getRequestbody() {
        return requestbody;
    }

    public void setRequestbody(String requestbody) {
        this.requestbody = requestbody == null ? null : requestbody.trim();
    }

    public String getDependent() {
        return dependent;
    }

    public void setDependent(String dependent) {
        this.dependent = dependent == null ? null : dependent.trim();
    }

    public String getRequestorder() {
        return requestorder;
    }

    public void setRequestorder(String requestorder) {
        this.requestorder = requestorder == null ? null : requestorder.trim();
    }

    public String getAssertexp() {
        return assertexp;
    }

    public void setAssertexp(String assertexp) {
        this.assertexp = assertexp == null ? null : assertexp.trim();
    }

    public Integer getModuleid() {
        return moduleid;
    }

    public void setModuleid(Integer moduleid) {
        this.moduleid = moduleid;
    }

    public Integer getDisable() {
        return disable;
    }

    public void setDisable(Integer disable) {
        this.disable = disable;
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