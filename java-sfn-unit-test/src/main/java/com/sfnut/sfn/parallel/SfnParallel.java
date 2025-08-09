package com.sfnut.sfn.parallel;

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
    private SfnParallelResultsAssembler<PAYLOAD> resultsAssembler;

    public SfnParallel(List<SfnStepWithPayload<PAYLOAD>> parallelFlows, 
                       SfnParallelResultsAssembler<PAYLOAD> resultsAssembler) {
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

        return resultsAssembler.execute(payload, results);
    }

    public List<SfnStepWithPayload<PAYLOAD>> getParallelFlows() {
        return parallelFlows;
    }

    public void setParallelFlows(List<SfnStepWithPayload<PAYLOAD>> parallelFlows) {
        this.parallelFlows = parallelFlows;
    }

    public SfnParallelResultsAssembler<PAYLOAD> getResultsAssembler() {
        return resultsAssembler;
    }

    public void setResultsAssembler(SfnParallelResultsAssembler<PAYLOAD> resultsAssembler) {
        this.resultsAssembler = resultsAssembler;
    }
}
