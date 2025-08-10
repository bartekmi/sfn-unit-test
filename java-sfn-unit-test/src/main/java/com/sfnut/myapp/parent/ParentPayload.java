package com.sfnut.myapp.parent;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ParentPayload {
    @JsonProperty("HappyPathData")
    private String happyPathData;
    
    @JsonProperty("DataFromChild")
    private String dataFromChild;
    
    @JsonProperty("ParallelA")
    private String parallelA;
    
    @JsonProperty("ParallelB")
    private String parallelB;
    
    @JsonProperty("AsyncOpStarted")
    private boolean asyncOpStarted;
    
    @JsonProperty("LambdaLoopIteration")
    private int lambdaLoopIteration;
    
    @JsonProperty("NestedObject")
    private NestedObject nestedObject;

    public ParentPayload(String originalInput, NestedObject nestedObject) {
        this.happyPathData = originalInput;
        this.nestedObject = nestedObject;
    }

    public ParentPayload() {}

    // Getters and setters
    public NestedObject getNestedObject() { return nestedObject; }
    public void setNestedObject(NestedObject nestedObject) { this.nestedObject = nestedObject; }
    
    public String getHappyPathData() { return happyPathData; }
    public void setHappyPathData(String happyPathData) { this.happyPathData = happyPathData; }
    
    public String getDataFromChild() { return dataFromChild; }
    public void setDataFromChild(String dataFromChild) { this.dataFromChild = dataFromChild; }
    
    public String getParallelA() { return parallelA; }
    public void setParallelA(String parallelA) { this.parallelA = parallelA; }
    
    public String getParallelB() { return parallelB; }
    public void setParallelB(String parallelB) { this.parallelB = parallelB; }
    
    public boolean isAsyncOpStarted() { return asyncOpStarted; }
    public void setAsyncOpStarted(boolean asyncOpStarted) { this.asyncOpStarted = asyncOpStarted; }
    
    public int getLambdaLoopIteration() { return lambdaLoopIteration; }
    public void setLambdaLoopIteration(int lambdaLoopIteration) { this.lambdaLoopIteration = lambdaLoopIteration; }
}
