package com.sfnut.myapp.parent;

import com.sfnut.sfn.parallel.SfnParallelResultsAssembler;
import java.util.List;

public class Step40_Parallel_Assember extends SfnParallelResultsAssembler<ParentPayload> {
    @Override
    public ParentPayload execute(ParentPayload original, List<ParentPayload> parallelOutputs) {
        original.setParallelA(parallelOutputs.get(0).getParallelA());
        original.setParallelB(parallelOutputs.get(1).getParallelB());

        return original;
    }
}
