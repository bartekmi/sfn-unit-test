package com.sfnut.myapp.parent;

import com.sfnut.sfn.lambda.SfnLambda;

public class Step40_Parallel_LambdaB extends SfnLambda<ParentPayload> {
    @Override
    public ParentPayload execute(ParentPayload payload) {
        payload.setParallelB("Bar");
        return payload;
    }
}
