package com.sfnut.sfn.nested;

import com.sfnut.sfn.ObjectMapping;
import com.sfnut.sfn.SfnStepWithPayload;
import com.sfnut.sfn.SfnWorkflow;
import com.sfnut.sfn.StepType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class SfnWorkflowInvocation<PARENT, CHILD> extends SfnStepWithPayload<PARENT> {
    @JsonIgnore
    private ObjectMapping<PARENT, CHILD> parentToChild;
    
    @JsonIgnore
    private SfnWorkflow<CHILD> nested;
    
    @JsonIgnore
    private ObjectMapping<CHILD, PARENT> childToParent;
    
    @JsonProperty("NestedWorkflowName")
    private String nestedWorkflowName;

    public SfnWorkflowInvocation(ObjectMapping<PARENT, CHILD> parentToChild,
                                SfnWorkflow<CHILD> nested,
                                ObjectMapping<CHILD, PARENT> childToParent) {
        super(StepType.INVOKE_NESTED);
        this.parentToChild = parentToChild;
        this.nested = nested;
        this.childToParent = childToParent;
        this.nestedWorkflowName = nested.getWorkflowName();
    }

    // Default constructor for Jackson
    public SfnWorkflowInvocation() {
        super(StepType.INVOKE_NESTED);
    }

    @Override
    public PARENT execute(PARENT payload) {
        CHILD childIn = parentToChild.map(payload);
        CHILD childOut = nested.run(childIn);
        return childToParent.mergeInto(childOut, payload);
    }

    public ObjectMapping<PARENT, CHILD> getParentToChild() {
        return parentToChild;
    }

    public SfnWorkflow<CHILD> getNested() {
        return nested;
    }

    public ObjectMapping<CHILD, PARENT> getChildToParent() {
        return childToParent;
    }

    public String getNestedWorkflowName() {
        return nestedWorkflowName;
    }
}
