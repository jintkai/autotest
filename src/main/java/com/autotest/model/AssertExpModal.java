package com.autotest.model;

public class AssertExpModal {
    private int id;
    private String type;
    private String variable;
    private boolean negation;
    private String rule;
    private String value;

    public AssertExpModal(int id, String type, String variable, boolean negation, String rule, String value) {
        this.id = id;
        this.type = type;
        this.variable = variable;
        this.negation = negation;
        this.rule = rule;
        this.value = value;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getVariable() {
        return variable;
    }

    public void setVariable(String variable) {
        this.variable = variable;
    }

    public boolean isNegation() {
        return negation;
    }

    public void setNegation(boolean negation) {
        this.negation = negation;
    }

    public String getRule() {
        return rule;
    }

    public void setRule(String rule) {
        this.rule = rule;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
