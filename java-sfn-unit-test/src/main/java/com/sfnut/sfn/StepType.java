package com.sfnut.sfn;

public enum StepType {
    LAMBDA,
    WAIT,
    ERROR,

    // Related to Lambda-Loop
    LAMBDA_LOOP,
    LAMBDA_LOOP_IS_COMPLETE,

    // Related to InvokeNested
    INVOKE_NESTED,
    INPUT_ADAPTER,
    OUTPUT_ADAPTER,

    // Related to Parallel
    PARALLEL,
    PARALLEL_RESULTS_ASSEMBLER
}
