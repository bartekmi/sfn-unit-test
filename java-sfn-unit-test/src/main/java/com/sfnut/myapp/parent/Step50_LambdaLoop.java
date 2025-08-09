package com.sfnut.myapp.parent;

import com.sfnut.sfn.lambda.SfnLambda;

public class Step50_LambdaLoop extends SfnLambda<ParentPayload> {
    @Override
    public ParentPayload execute(ParentPayload payload) {
        payload.setAsyncOpStarted(true);
        return payload;
    }
}
