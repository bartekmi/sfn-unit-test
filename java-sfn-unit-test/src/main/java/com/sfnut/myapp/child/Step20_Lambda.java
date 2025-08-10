package com.sfnut.myapp.child;

import com.sfnut.sfn.lambda.SfnLambda;

public class Step20_Lambda extends SfnLambda<ChildPayload> {
    @Override
    public ChildPayload execute(ChildPayload input) {
        input.setHappyPathData(input.getHappyPathData() + " > Child 20");
        input.setNestedFromParent("***" + input.getNestedFromParent() + "***");

        return input;
    }
}
