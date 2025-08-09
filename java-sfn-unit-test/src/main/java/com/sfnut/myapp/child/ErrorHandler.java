package com.sfnut.myapp.child;

import com.sfnut.sfn.SfnErrorHandler;
import com.sfnut.sfn.error.SfnErrorHandlerInput;

public class ErrorHandler extends SfnErrorHandler<ChildPayload> {
    @Override
    public SfnErrorHandlerInput<ChildPayload> execute(SfnErrorHandlerInput<ChildPayload> payload) {
        return payload;
    }
}
