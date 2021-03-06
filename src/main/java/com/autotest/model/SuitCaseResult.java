package com.autotest.model;

import java.util.Date;

public class SuitCaseResult {
    private Integer id;

    private Integer suitcaseid;
    private String casename;
    private Integer suitid;

    private Integer buildid;

    private String requestHeader;

    private String requestBody;

    private String requestUrl;

    private String responseheader;

    private String responsebody;

    private String responsecode;

    private Integer responsetime;

    private String assertlog;

    private Integer status;

    private Date timestamp;

    private Date updatetime;

    private String assertExp;
    private String casetype;
    private Integer maincase;

    public String getCasename() {
        return casename;
    }

    public void setCasename(String casename) {
        this.casename = casename;
    }

    public String getCasetype() {
        return casetype;
    }

    public void setCasetype(String casetype) {
        this.casetype = casetype;
    }

    public Integer getMaincase() {
        return maincase;
    }

    public void setMaincase(Integer maincase) {
        this.maincase = maincase;
    }

    public String getAssertExp() {
        return assertExp;
    }

    public void setAssertExp(String assertExp) {
        this.assertExp = assertExp;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSuitcaseid() {
        return suitcaseid;
    }

    public void setSuitcaseid(Integer suitcaseid) {
        this.suitcaseid = suitcaseid;
    }

    public Integer getSuitid() {
        return suitid;
    }

    public void setSuitid(Integer suitid) {
        this.suitid = suitid;
    }

    public Integer getBuildid() {
        return buildid;
    }

    public void setBuildid(Integer buildid) {
        this.buildid = buildid;
    }

    public String getResponseheader() {
        return responseheader;
    }

    public void setResponseheader(String responseheader) {
        this.responseheader = responseheader == null ? null : responseheader.trim();
    }

    public String getResponsebody() {
        return responsebody;
    }

    public void setResponsebody(String responsebody) {
        this.responsebody = responsebody == null ? null : responsebody.trim();
    }

    public String getResponsecode() {
        return responsecode;
    }

    public void setResponsecode(String responsecode) {
        this.responsecode = responsecode == null ? null : responsecode.trim();
    }

    public Integer getResponsetime() {
        return responsetime;
    }

    public void setResponsetime(Integer responsetime) {
        this.responsetime = responsetime;
    }

    public String getAssertlog() {
        return assertlog;
    }

    public void setAssertlog(String assertlog) {
        this.assertlog = assertlog == null ? null : assertlog.trim();
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

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public String getRequestHeader() {
        return requestHeader;
    }

    public void setRequestHeader(String requestHeader) {
        this.requestHeader = requestHeader;
    }

    public String getRequestBody() {
        return requestBody;
    }

    public void setRequestBody(String requestBody) {
        this.requestBody = requestBody;
    }

    public String getRequestUrl() {
        return requestUrl;
    }

    public void setRequestUrl(String requestUrl) {
        this.requestUrl = requestUrl;
    }
}