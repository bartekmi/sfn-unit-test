package com.sfnut.myapp.parent;

import com.sfnut.sfn.lambda.SfnLambda;

public class Step10_Lambda extends SfnLambda<ParentPayload> {
    @Override
    public ParentPayload execute(ParentPayload payload) {
        payload.setHappyPathData(payload.getHappyPathData() + " > 10");
        return payload;
    }
}
