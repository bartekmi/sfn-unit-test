package com.sfnut.sfn.parallel;

import com.sfnut.sfn.ObjectMapping;
import com.sfnut.sfn.SfnStepWithPayload;
import com.sfnut.sfn.StepType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;

public class SfnParallel<PAYLOAD> extends SfnStepWithPayload<PAYLOAD> {
    @JsonProperty("ParallelFlows")
    private List<SfnStepWithPayload<PAYLOAD>> parallelFlows = new ArrayList<>();
    
    @JsonIgnore
    private ObjectMapping<List<PAYLOAD>, PAYLOAD> resultsAssembler;

    public SfnParallel(List<SfnStepWithPayload<PAYLOAD>> parallelFlows, 
                       ObjectMapping<List<PAYLOAD>, PAYLOAD> resultsAssembler) {
        super(StepType.PARALLEL);
        this.parallelFlows = parallelFlows;
        this.resultsAssembler = resultsAssembler;
    }

    // Default constructor for Jackson
    public SfnParallel() {
        super(StepType.PARALLEL);
    }

    @Override
    public PAYLOAD execute(PAYLOAD payload) {
        // Though we do it linearly here, there is nothing stopping us from spinning up multiple
        // threads and really doing it in parallel. This would be useful if real dependencies are
        // being invoked.
        List<PAYLOAD> results = new ArrayList<>();
        for (SfnStepWithPayload<PAYLOAD> flow : parallelFlows) {
            results.add(flow.execute(payload));
        }

        return resultsAssembler.mergeInto(results, payload);
    }
}
