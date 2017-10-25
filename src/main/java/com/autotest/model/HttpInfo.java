package com.autotest.model;

public class HttpInfo {
    private Long responseTime;
    private String responseCode;
    private String responseBody;
    private String responseHeader;
    private String responseLog;
    private Integer  isSuccess;
    private String requestUrl;
    private String requestHeader;
    private String requestParam;

    public String getRequestUrl() {
        return requestUrl;
    }

    public void setRequestUrl(String requestUrl) {
        this.requestUrl = requestUrl;
    }

    public String getRequestHeader() {
        return requestHeader;
    }

    public void setRequestHeader(String requestHeader) {
        this.requestHeader = requestHeader;
    }

    public String getRequestParam() {
        return requestParam;
    }

    public void setRequestParam(String requestParam) {
        this.requestParam = requestParam;
    }

    public Long getResponseTime() {
        return responseTime;
    }

    public void setResponseTime(Long responseTime) {
        this.responseTime = responseTime;
    }

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public String getResponseBody() {
        return responseBody;
    }

    public void setResponseBody(String responseBody) {
        this.responseBody = responseBody;
    }

    public String getResponseHeader() {
        return responseHeader;
    }

    public void setResponseHeader(String responseHeader) {
        this.responseHeader = responseHeader;
    }

    public String getResponseLog() {
        return responseLog;
    }

    public void setResponseLog(String responseLog) {
        this.responseLog = responseLog;
    }

    public Integer getIsSuccess() {
        return isSuccess;
    }

    public void setIsSuccess(Integer isSuccess) {
        this.isSuccess = isSuccess;
    }


    public HttpInfo() {
        this.responseTime = Long.valueOf(0);
        this.responseCode = "";
        this.responseBody = "";
        this.responseHeader = "";
        this.responseLog = "";
        this.isSuccess = 0;
        this.requestUrl = "";
        this.requestHeader = "";
        this.requestParam = "";
    }
}
