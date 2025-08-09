package com.sfnut.myapp.child;

import com.sfnut.sfn.lambda.SfnLambda;

public class Step10_Lambda extends SfnLambda<ChildPayload> {
    @Override
    public ChildPayload execute(ChildPayload input) {
        input.setHappyPathData(input.getHappyPathData() + " > Child 10");
        return input;
    }
}
