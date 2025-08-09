package com.sfnut.myapp.parent;

import com.sfnut.sfn.lambdawithloop.SfnIsComplete;
import com.sfnut.sfn.lambdawithloop.SfnIsCompleteOutput;

public class Step50_LambdaLoopGet extends SfnIsComplete<ParentPayload> {
    @Override
    public SfnIsCompleteOutput<ParentPayload> isComplete(ParentPayload payload) {
        boolean isComplete = false;
        
        if (payload.getLambdaLoopIteration() >= 5) {
            isComplete = true;
        } else {
            payload.setLambdaLoopIteration(payload.getLambdaLoopIteration() + 1);
        }

        return new SfnIsCompleteOutput<>(payload, isComplete);
    }
}
