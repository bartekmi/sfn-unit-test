package com.sfnut.sfn;

import com.sfnut.sfn.error.SfnErrorHandlerInput;

public abstract class SfnErrorHandler<PAYLOAD> extends SfnStep {
    
    public SfnErrorHandler() {
        super(StepType.ERROR);
    }

    public abstract SfnErrorHandlerInput<PAYLOAD> execute(SfnErrorHandlerInput<PAYLOAD> payload);
}
