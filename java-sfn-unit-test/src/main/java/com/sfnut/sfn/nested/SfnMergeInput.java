package com.sfnut.sfn.nested;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SfnMergeInput<PARENT, CHILD> {
    @JsonProperty("Parent")
    private PARENT parent;
    
    @JsonProperty("Child")
    private CHILD child;

    public SfnMergeInput(PARENT parent, CHILD child) {
        this.parent = parent;
        this.child = child;
    }

    public SfnMergeInput() {}

    // Getters and setters
    public PARENT getParent() { return parent; }
    public void setParent(PARENT parent) { this.parent = parent; }
    
    public CHILD getChild() { return child; }
    public void setChild(CHILD child) { this.child = child; }
}
