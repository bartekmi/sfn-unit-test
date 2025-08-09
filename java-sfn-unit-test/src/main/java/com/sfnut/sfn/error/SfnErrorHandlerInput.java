package com.sfnut.sfn.error;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SfnErrorHandlerInput<PAYLOAD> {
    @JsonProperty("Payload")
    private PAYLOAD payload;
    
    @JsonProperty("ErrorMessage")
    private String errorMessage;

    public SfnErrorHandlerInput(PAYLOAD payload, String errorMessage) {
        this.payload = payload;
        this.errorMessage = errorMessage;
    }

    public SfnErrorHandlerInput() {}

    // Getters and setters
    public PAYLOAD getPayload() { return payload; }
    public void setPayload(PAYLOAD payload) { this.payload = payload; }
    
    public String getErrorMessage() { return errorMessage; }
    public void setErrorMessage(String errorMessage) { this.errorMessage = errorMessage; }
}
