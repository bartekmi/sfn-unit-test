package com.sfnut.myapp.parent;

import com.sfnut.sfn.lambda.SfnLambda;

public class Step30_Lambda extends SfnLambda<ParentPayload> {
    @Override
    public ParentPayload execute(ParentPayload payload) {
        payload.setHappyPathData(payload.getHappyPathData() + " > 30");
        return payload;
    }
}
