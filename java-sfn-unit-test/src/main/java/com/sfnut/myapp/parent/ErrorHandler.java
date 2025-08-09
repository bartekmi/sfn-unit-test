package com.sfnut.myapp.parent;

import com.sfnut.sfn.SfnErrorHandler;
import com.sfnut.sfn.error.SfnErrorHandlerInput;

public class ErrorHandler extends SfnErrorHandler<ParentPayload> {
    @Override
    public SfnErrorHandlerInput<ParentPayload> execute(SfnErrorHandlerInput<ParentPayload> payload) {
        return payload;
    }
}
