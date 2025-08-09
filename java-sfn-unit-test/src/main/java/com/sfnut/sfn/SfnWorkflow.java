package com.sfnut.sfn;

import com.sfnut.sfn.error.SfnErrorHandlerInput;
import com.sfnut.sfn.lambda.SfnLambda;
import com.sfnut.sfn.lambdawithloop.SfnIsComplete;
import com.sfnut.sfn.lambdawithloop.SfnLambdaWithLoop;
import com.sfnut.sfn.nested.SfnMerge;
import com.sfnut.sfn.nested.SfnMergeInput;
import com.sfnut.sfn.nested.SfnTransform;
import com.sfnut.sfn.nested.SfnWorkflowInvocation;
import com.sfnut.sfn.parallel.SfnParallel;
import com.sfnut.sfn.parallel.SfnParallelResultsAssembler;
import com.sfnut.sfn.wait.SfnWait;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public abstract class SfnWorkflow<PAYLOAD> {
    @JsonProperty("Steps")
    private List<SfnStep> steps = new ArrayList<>();
    
    @JsonProperty("ErrorHandler")
    private SfnErrorHandler<PAYLOAD> errorHandler;
    
    @JsonProperty("WorkflowName")
    private String workflowName;

    public SfnWorkflow(SfnErrorHandler<PAYLOAD> errorHandler) {
        this.errorHandler = errorHandler;
        this.workflowName = this.getClass().getName();
    }

    public abstract PAYLOAD run(PAYLOAD input);

    protected void handleErrorInternal(PAYLOAD payload, Exception e) {
        // Do logging, assertions, etc
        errorHandler.execute(new SfnErrorHandlerInput<>(payload, e.getMessage()));
    }

    public PAYLOAD lambda(SfnLambda<PAYLOAD> lambda, PAYLOAD input) {
        steps.add(lambda);

        try {
            return lambda.execute(input);
        } catch (Exception e) {
            handleErrorInternal(input, e);
            throw e;
        }
    }

    public void waitStep(String stepName, Duration duration) {
        steps.add(new SfnWait<>(stepName, duration));
    }

    public PAYLOAD lambdaLoop(SfnLambda<PAYLOAD> lambda,
                             SfnIsComplete<PAYLOAD> isComplete,
                             PAYLOAD payload) {
        SfnLambdaWithLoop<PAYLOAD> lambdaWithLoop = new SfnLambdaWithLoop<>(lambda, isComplete);
        steps.add(lambdaWithLoop);

        try {
            return lambdaWithLoop.execute(payload);
        } catch (Exception e) {
            handleErrorInternal(payload, e);
            throw e;
        }
    }

    public PAYLOAD parallel(List<SfnStepWithPayload<PAYLOAD>> stepList,
                           SfnParallelResultsAssembler<PAYLOAD> assembler,
                           PAYLOAD payload) {
        SfnParallel<PAYLOAD> parallel = new SfnParallel<>(stepList, assembler);

        steps.add(parallel);
        steps.add(assembler);

        try {
            return parallel.execute(payload);
        } catch (Exception e) {
            handleErrorInternal(payload, e);
            throw e;
        }
    }

    public <CHILD> PAYLOAD nestedSfn(SfnTransform<PAYLOAD, CHILD> transform,
                                    SfnWorkflow<CHILD> nested,
                                    SfnMerge<PAYLOAD, CHILD> merge,
                                    PAYLOAD payload) {
        SfnWorkflowInvocation<PAYLOAD, CHILD> nestedInvocation = 
            new SfnWorkflowInvocation<>(transform, nested, merge);

        steps.add(transform);
        steps.add(nestedInvocation);
        steps.add(merge);

        try {
            return nestedInvocation.execute(payload);
        } catch (Exception e) {
            handleErrorInternal(payload, e);
            throw e;
        }
    }

    // Getters and setters
    public List<SfnStep> getSteps() {
        return steps;
    }

    public void setSteps(List<SfnStep> steps) {
        this.steps = steps;
    }

    public SfnErrorHandler<PAYLOAD> getErrorHandler() {
        return errorHandler;
    }

    public void setErrorHandler(SfnErrorHandler<PAYLOAD> errorHandler) {
        this.errorHandler = errorHandler;
    }

    public String getWorkflowName() {
        return workflowName;
    }

    public void setWorkflowName(String workflowName) {
        this.workflowName = workflowName;
    }
}
