package com.sfnut.sfn.lambdawithloop;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SfnIsCompleteOutput<PAYLOAD> {
    @JsonProperty("Payload")
    private PAYLOAD payload;
    
    @JsonProperty("IsComplete")
    private boolean isComplete;

    public SfnIsCompleteOutput(PAYLOAD payload, boolean isComplete) {
        this.payload = payload;
        this.isComplete = isComplete;
    }

    public SfnIsCompleteOutput() {}

    // Getters and setters
    public PAYLOAD getPayload() { return payload; }
    public void setPayload(PAYLOAD payload) { this.payload = payload; }
    
    public boolean isComplete() { return isComplete; }
    public void setComplete(boolean complete) { isComplete = complete; }
}
