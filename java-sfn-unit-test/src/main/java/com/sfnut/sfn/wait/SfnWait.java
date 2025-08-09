package com.sfnut.sfn.wait;

import com.sfnut.sfn.SfnStepWithPayload;
import com.sfnut.sfn.StepType;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.Duration;

public class SfnWait<PAYLOAD> extends SfnStepWithPayload<PAYLOAD> {
    @JsonProperty("StepName")
    private final String stepName;
    
    @JsonProperty("Duration")
    private final Duration duration;

    public SfnWait(String stepName, Duration duration) {
        super(StepType.WAIT);
        this.stepName = stepName;
        this.duration = duration;
    }

    @Override
    public PAYLOAD execute(PAYLOAD input) {
        // TODO: Increase fake time
        return input;
    }

    public String getStepName() {
        return stepName;
    }

    public Duration getDuration() {
        return duration;
    }
}
