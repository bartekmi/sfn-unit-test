package com.sfnut.myapp.parent;

import com.sfnut.sfn.lambda.SfnLambda;

public class Step40_Parallel_LambdaA extends SfnLambda<ParentPayload> {
    @Override
    public ParentPayload execute(ParentPayload payload) {
        payload.setParallelA("Foo");
        return payload;
    }
}
