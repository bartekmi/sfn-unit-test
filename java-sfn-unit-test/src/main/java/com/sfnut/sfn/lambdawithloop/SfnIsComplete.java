package com.sfnut.sfn.lambdawithloop;

import com.sfnut.sfn.SfnStep;
import com.sfnut.sfn.StepType;

public abstract class SfnIsComplete<PAYLOAD> extends SfnStep {
    
    public SfnIsComplete() {
        super(StepType.LAMBDA_LOOP_IS_COMPLETE);
    }

    public abstract SfnIsCompleteOutput<PAYLOAD> isComplete(PAYLOAD payload);
}
