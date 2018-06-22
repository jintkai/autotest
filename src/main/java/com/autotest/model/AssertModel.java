package com.autotest.model;

public class AssertModel {
    private String assertType;
    private Integer success;
    private Object message;

    public String getAssertType() {
        return assertType;
    }

    public void setAssertType(String assertType) {
        this.assertType = assertType;
    }

    public Integer getSuccess() {
        return success;
    }

    public void setSuccess(Integer success) {
        this.success = success;
    }

    public Object getMessage() {
        return message;
    }

    public void setMessage(Object message) {
        this.message = message;
    }
}
