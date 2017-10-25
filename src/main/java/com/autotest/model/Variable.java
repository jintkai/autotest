package com.autotest.model;

import java.util.Date;

public class Variable {
    private Integer variableid;
    private String type;
    private Integer suitcaseId;
    private Integer suitid;
    private String name;
    private String varExpress;
    private Integer sequence;
    private Date timestamp;
    private Date updatetime;
    public Variable(){};
    public Variable(Variable v){
        this.variableid = v.getVariableid();
    }

    public Integer getSuitcaseId() {
        return suitcaseId;
    }

    public void setSuitcaseId(Integer suitcaseId) {
        this.suitcaseId = suitcaseId;
    }

    public Integer getVariableid() {
        return variableid;
    }

    public void setVariableid(Integer id) {
        this.variableid = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getSuitid() {
        return suitid;
    }

    public void setSuitid(Integer suitid) {
        this.suitid = suitid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVarExpress() {
        return varExpress;
    }

    public void setVarExpress(String varExpress) {
        this.varExpress = varExpress;
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

    public Integer getSequence() {
        return sequence;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }

}
