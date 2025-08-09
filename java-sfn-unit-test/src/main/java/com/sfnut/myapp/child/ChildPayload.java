package com.sfnut.myapp.child;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ChildPayload {
    @JsonProperty("HappyPathData")
    private String happyPathData;
    
    @JsonProperty("ServiceChargeInDollars")
    private int serviceChargeInDollars;

    public ChildPayload(String fromParent) {
        this.happyPathData = fromParent;
    }

    // Default constructor for Jackson
    public ChildPayload() {}

    public String getHappyPathData() {
        return happyPathData;
    }

    public void setHappyPathData(String happyPathData) {
        this.happyPathData = happyPathData;
    }

    public int getServiceChargeInDollars() {
        return serviceChargeInDollars;
    }

    public void setServiceChargeInDollars(int serviceChargeInDollars) {
        this.serviceChargeInDollars = serviceChargeInDollars;
    }
}
