package com.sfnut.myapp.child;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ChildPayload {
    @JsonProperty("HappyPathData")
    private String happyPathData;
    
    @JsonProperty("NestedFromParent")
    private String nestedFromParent;

    public ChildPayload() {}

    // Getters and setters
    public String getHappyPathData() { return happyPathData; }
    public void setHappyPathData(String happyPathData) { this.happyPathData = happyPathData; }
    
    public String getNestedFromParent() { return nestedFromParent; }
    public void setNestedFromParent(String nestedFromParent) { this.nestedFromParent = nestedFromParent; }
}
