package com.sfnut.sfn;

public abstract class SfnStepWithPayload<PAYLOAD> extends SfnStep {
    
    protected SfnStepWithPayload(StepType type) {
        super(type);
    }

    public abstract PAYLOAD execute(PAYLOAD input);
}
