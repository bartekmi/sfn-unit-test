package com.sfnut.sfn.parallel;

import com.sfnut.sfn.SfnStep;
import com.sfnut.sfn.StepType;
import java.util.List;

public abstract class SfnParallelResultsAssembler<PAYLOAD> extends SfnStep {
    
    public SfnParallelResultsAssembler() {
        super(StepType.PARALLEL_RESULTS_ASSEMBLER);
    }

    public abstract PAYLOAD execute(PAYLOAD original, List<PAYLOAD> parallelOutputs);
}
