package com.sfnut.sfn;

import com.fasterxml.jackson.annotation.JsonProperty;

public abstract class SfnStep {
    @JsonProperty("Type")
    private StepType type;
    
    @JsonProperty("ClassName")
    private String className;

    protected SfnStep(StepType type) {
        this.type = type;
        // Set className only for non-generic types
        Class<?> clazz = this.getClass();
        if (clazz.getTypeParameters().length == 0) {
            this.className = clazz.getName();
        }
    }

    public StepType getType() {
        return type;
    }

    public void setType(StepType type) {
        this.type = type;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }
}
