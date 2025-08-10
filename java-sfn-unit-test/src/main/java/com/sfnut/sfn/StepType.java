package com.sfnut.sfn;

public enum StepType {
    LAMBDA,
    WAIT,
    ERROR,
    INVOKE_NESTED,

    // Related to Lambda-Loop
    LAMBDA_LOOP,
    LAMBDA_LOOP_IS_COMPLETE,

    // Related to Parallel
    PARALLEL,
    PARALLEL_RESULTS_ASSEMBLER
}
