package com.sfnut.myapp.parent;

import com.sfnut.sfn.ObjectMapping;
import com.sfnut.sfn.SfnWorkflow;
import com.sfnut.myapp.child.ChildPayload;
import com.sfnut.myapp.child.ChildWorkflow;
import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class ParentWorkflow extends SfnWorkflow<ParentPayload> {
    
    public ParentWorkflow() {
        super(new ErrorHandler());
    }

    @Override
    public ParentPayload run(ParentPayload payload) {
        payload = lambda(new Step10_Lambda(), payload);

        payload = nestedSfn(
            new ObjectMapping<ParentPayload,ChildPayload>(ChildPayload.class, Map.of(
              "happyPathData", "happyPathData",
              "nestedFromParent", "nestedObject.nestedField"
            )),

            new ChildWorkflow(),

            new ObjectMapping<ChildPayload,ParentPayload>(ParentPayload.class, Map.of(
              "happyPathData", "happyPathData",
              "dataFromChild", "nestedFromParent"
            )),
            payload);

        payload = lambda(new Step30_Lambda(), payload);

        payload = parallel(Arrays.asList(
                new Step40_Parallel_LambdaA(),
                new Step40_Parallel_LambdaB()
            ),
            new ObjectMapping<List<ParentPayload>,ParentPayload>(ParentPayload.class, Map.of(
              "parallelA", "[0].parallelA",
              "parallelB", "[1].parallelB"
            )),
            payload);

        payload = lambdaLoop(
            new Step50_LambdaLoop(),
            new Step50_LambdaLoopGet(),
            payload
        );

        waitStep("WaitAtEnd", Duration.ofSeconds(2));
        
        return payload;
    }
}
