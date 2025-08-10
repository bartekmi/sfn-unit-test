package com.sfnut.myapp.parent;

import com.sfnut.sfn.nested.SfnTransform;
import com.sfnut.myapp.child.ChildPayload;

public class Step20_Nested_InputAdapter extends SfnTransform<ParentPayload, ChildPayload> {
    @Override
    public ChildPayload execute(ParentPayload input) {
        ChildPayload payload = new ChildPayload();
        payload.setHappyPathData(input.getHappyPathData());
        payload.setNestedFromParent(input.getNestedObject().getNestedField());

        return payload;
    }
}
