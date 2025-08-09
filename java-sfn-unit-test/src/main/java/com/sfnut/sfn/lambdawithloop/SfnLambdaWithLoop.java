package com.sfnut.sfn.lambdawithloop;

import com.sfnut.sfn.SfnStepWithPayload;
import com.sfnut.sfn.StepType;
import com.sfnut.sfn.lambda.SfnLambda;
import com.fasterxml.jackson.annotation.JsonProperty;

public class SfnLambdaWithLoop<PAYLOAD> extends SfnStepWithPayload<PAYLOAD> {
    @JsonProperty("Lambda")
    private SfnLambda<PAYLOAD> lambda;
    
    @JsonProperty("IsComplete")
    private SfnIsComplete<PAYLOAD> isComplete;
    
    @JsonProperty("MaxChecks")
    private int maxChecks = 10;

    public SfnLambdaWithLoop(SfnLambda<PAYLOAD> lambda, SfnIsComplete<PAYLOAD> isComplete) {
        super(StepType.LAMBDA_LOOP);
        this.lambda = lambda;
        this.isComplete = isComplete;
    }

    // Default constructor for Jackson
    public SfnLambdaWithLoop() {
        super(StepType.LAMBDA_LOOP);
    }

    @Override
    public PAYLOAD execute(PAYLOAD payload) {
        payload = lambda.execute(payload);

        for (int i = 0; i < maxChecks; i++) {
            if (isComplete.isComplete(payload).isComplete()) {
                return payload;
            }
        }

        throw new RuntimeException(String.format("Exceeded maximum number of checks allowed (%d)", maxChecks));
    }

    public SfnLambda<PAYLOAD> getLambda() {
        return lambda;
    }

    public void setLambda(SfnLambda<PAYLOAD> lambda) {
        this.lambda = lambda;
    }

    public SfnIsComplete<PAYLOAD> getIsComplete() {
        return isComplete;
    }

    public void setIsComplete(SfnIsComplete<PAYLOAD> isComplete) {
        this.isComplete = isComplete;
    }

    public int getMaxChecks() {
        return maxChecks;
    }

    public void setMaxChecks(int maxChecks) {
        this.maxChecks = maxChecks;
    }
}
