package com.sfnut.sfn.nested;

import com.sfnut.sfn.SfnStep;
import com.sfnut.sfn.StepType;

public abstract class SfnMerge<PARENT, CHILD> extends SfnStep {
    
    public SfnMerge() {
        super(StepType.OUTPUT_ADAPTER);
    }

    public abstract PARENT execute(SfnMergeInput<PARENT, CHILD> input);
}
