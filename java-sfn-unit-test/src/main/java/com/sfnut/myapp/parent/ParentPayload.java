package com.sfnut.myapp.parent;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ParentPayload {
    @JsonProperty("HappyPathData")
    private String happyPathData;
    
    @JsonProperty("DataFromChild")
    private Integer dataFromChild;
    
    @JsonProperty("ParallelA")
    private String parallelA;
    
    @JsonProperty("ParallelB")
    private String parallelB;
    
    @JsonProperty("AsyncOpStarted")
    private boolean asyncOpStarted;
    
    @JsonProperty("LambdaLoopIteration")
    private int lambdaLoopIteration;

    public ParentPayload(String originalInput) {
        this.happyPathData = originalInput;
    }

    // Default constructor for Jackson
    public ParentPayload() {}

    public String getHappyPathData() {
        return happyPathData;
    }

    public void setHappyPathData(String happyPathData) {
        this.happyPathData = happyPathData;
    }

    public Integer getDataFromChild() {
        return dataFromChild;
    }

    public void setDataFromChild(Integer dataFromChild) {
        this.dataFromChild = dataFromChild;
    }

    public String getParallelA() {
        return parallelA;
    }

    public void setParallelA(String parallelA) {
        this.parallelA = parallelA;
    }

    public String getParallelB() {
        return parallelB;
    }

    public void setParallelB(String parallelB) {
        this.parallelB = parallelB;
    }

    public boolean isAsyncOpStarted() {
        return asyncOpStarted;
    }

    public void setAsyncOpStarted(boolean asyncOpStarted) {
        this.asyncOpStarted = asyncOpStarted;
    }

    public int getLambdaLoopIteration() {
        return lambdaLoopIteration;
    }

    public void setLambdaLoopIteration(int lambdaLoopIteration) {
        this.lambdaLoopIteration = lambdaLoopIteration;
    }
}
