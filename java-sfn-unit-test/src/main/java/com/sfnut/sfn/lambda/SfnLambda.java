package com.sfnut.sfn.lambda;

import com.sfnut.sfn.SfnStepWithPayload;
import com.sfnut.sfn.StepType;

public abstract class SfnLambda<PAYLOAD> extends SfnStepWithPayload<PAYLOAD> {
    
    public SfnLambda() {
        super(StepType.LAMBDA);
    }
}
