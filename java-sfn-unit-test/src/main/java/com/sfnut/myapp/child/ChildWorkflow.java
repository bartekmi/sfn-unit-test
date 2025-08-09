package com.sfnut.myapp.child;

import com.sfnut.sfn.SfnWorkflow;

public class ChildWorkflow extends SfnWorkflow<ChildPayload> {
    public ChildWorkflow() {
        super(new ErrorHandler());
    }

    @Override
    public ChildPayload run(ChildPayload payload) {
        payload = lambda(new Step10_Lambda(), payload);
        payload = lambda(new Step20_Lambda(), payload);

        return payload;
    }
}
