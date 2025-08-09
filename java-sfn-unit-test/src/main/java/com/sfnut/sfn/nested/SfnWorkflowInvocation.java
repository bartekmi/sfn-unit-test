package com.sfnut.sfn.nested;

import com.sfnut.sfn.SfnStepWithPayload;
import com.sfnut.sfn.SfnWorkflow;
import com.sfnut.sfn.StepType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class SfnWorkflowInvocation<PARENT, CHILD> extends SfnStepWithPayload<PARENT> {
    @JsonIgnore
    private SfnTransform<PARENT, CHILD> transformInput;
    
    @JsonIgnore
    private SfnWorkflow<CHILD> nested;
    
    @JsonIgnore
    private SfnMerge<PARENT, CHILD> mergeOutput;
    
    @JsonProperty("NestedWorkflowName")
    private String nestedWorkflowName;

    public SfnWorkflowInvocation(SfnTransform<PARENT, CHILD> transformInput,
                                SfnWorkflow<CHILD> nested,
                                SfnMerge<PARENT, CHILD> mergeOutput) {
        super(StepType.INVOKE_NESTED);
        this.transformInput = transformInput;
        this.nested = nested;
        this.mergeOutput = mergeOutput;
        this.nestedWorkflowName = nested.getWorkflowName();
    }

    // Default constructor for Jackson
    public SfnWorkflowInvocation() {
        super(StepType.INVOKE_NESTED);
    }

    @Override
    public PARENT execute(PARENT payload) {
        CHILD childIn = transformInput.execute(payload);
        CHILD childOut = nested.run(childIn);
        return mergeOutput.execute(new SfnMergeInput<>(payload, childOut));
    }

    public SfnTransform<PARENT, CHILD> getTransformInput() {
        return transformInput;
    }

    public void setTransformInput(SfnTransform<PARENT, CHILD> transformInput) {
        this.transformInput = transformInput;
    }

    public SfnWorkflow<CHILD> getNested() {
        return nested;
    }

    public void setNested(SfnWorkflow<CHILD> nested) {
        this.nested = nested;
        if (nested != null) {
            this.nestedWorkflowName = nested.getWorkflowName();
        }
    }

    public SfnMerge<PARENT, CHILD> getMergeOutput() {
        return mergeOutput;
    }

    public void setMergeOutput(SfnMerge<PARENT, CHILD> mergeOutput) {
        this.mergeOutput = mergeOutput;
    }

    public String getNestedWorkflowName() {
        return nestedWorkflowName;
    }

    public void setNestedWorkflowName(String nestedWorkflowName) {
        this.nestedWorkflowName = nestedWorkflowName;
    }
}
