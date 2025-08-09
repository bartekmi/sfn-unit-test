package com.sfnut.sfn.nested;

import com.sfnut.sfn.SfnStep;
import com.sfnut.sfn.StepType;

public abstract class SfnTransform<INPUT, OUTPUT> extends SfnStep {
    
    public SfnTransform() {
        super(StepType.INPUT_ADAPTER);
    }

    public abstract OUTPUT execute(INPUT input);
}
