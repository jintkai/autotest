package com.autotest.model;

import java.util.Date;

public class SuitCaseReport {
    private Integer id;

    private Integer suitid;

    private Integer buildid;

    private Integer total;

    private Integer success;

    private Integer fail;

    private Float successper;

    private Integer unexe;

    private Date timestamp;

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

    public Integer getBuildid() {
        return buildid;
    }

    public void setBuildid(Integer buildid) {
        this.buildid = buildid;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getSuccess() {
        return success;
    }

    public void setSuccess(Integer success) {
        this.success = success;
    }

    public Integer getFail() {
        return fail;
    }

    public void setFail(Integer fail) {
        this.fail = fail;
    }

    public Float getSuccessper() {
        return successper;
    }

    public void setSuccessper(Float successper) {
        this.successper = successper;
    }

    public Integer getUnexe() {
        return unexe;
    }

    public void setUnexe(Integer unexe) {
        this.unexe = unexe;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}