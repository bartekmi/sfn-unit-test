package com.sfnut.myapp.parent;

import com.sfnut.sfn.nested.SfnMerge;
import com.sfnut.sfn.nested.SfnMergeInput;
import com.sfnut.myapp.child.ChildPayload;

public class Step20_Nested_OutputAdapter extends SfnMerge<ParentPayload, ChildPayload> {
    @Override
    public ParentPayload execute(SfnMergeInput<ParentPayload, ChildPayload> input) {
        input.getParent().setHappyPathData(input.getChild().getHappyPathData());
        input.getParent().setDataFromChild(input.getChild().getServiceChargeInDollars());

        return input.getParent();
    }
}
