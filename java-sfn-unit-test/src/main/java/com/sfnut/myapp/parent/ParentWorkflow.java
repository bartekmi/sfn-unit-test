package com.sfnut.myapp.parent;

import com.sfnut.sfn.FieldMapping;
import com.sfnut.sfn.ObjectMapping;
import com.sfnut.sfn.SfnWorkflow;
import com.sfnut.myapp.child.ChildPayload;
import com.sfnut.myapp.child.ChildWorkflow;
import java.time.Duration;
import java.util.Arrays;
import java.util.List;

public class ParentWorkflow extends SfnWorkflow<ParentPayload> {
    
    public ParentWorkflow() {
        super(new ErrorHandler());
    }

    @Override
    public ParentPayload run(ParentPayload payload) {
        payload = lambda(new Step10_Lambda(), payload);

        payload = nestedSfn(
            new ObjectMapping<ParentPayload,ChildPayload>(ChildPayload.class, List.of(
              new FieldMapping<>("happyPathData", "happyPathData"),
              new FieldMapping<>("nestedFromParent", "nestedObject.nestedField")
            )),

            new ChildWorkflow(),

            new ObjectMapping<ChildPayload,ParentPayload>(ParentPayload.class, List.of(
              new FieldMapping<>("happyPathData", "happyPathData"),
              new FieldMapping<>("dataFromChild", "nestedFromParent")
            )),
            payload);

        payload = lambda(new Step30_Lambda(), payload);

        payload = parallel(Arrays.asList(
                new Step40_Parallel_LambdaA(),
                new Step40_Parallel_LambdaB()
            ),
            new Step40_Parallel_Assember(),
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
