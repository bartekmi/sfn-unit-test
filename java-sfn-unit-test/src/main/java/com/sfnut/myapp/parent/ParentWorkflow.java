package com.sfnut.myapp.parent;

import com.sfnut.sfn.SfnWorkflow;
import com.sfnut.myapp.child.ChildWorkflow;
import java.time.Duration;
import java.util.Arrays;

public class ParentWorkflow extends SfnWorkflow<ParentPayload> {
    
    public ParentWorkflow() {
        super(new ErrorHandler());
    }

    @Override
    public ParentPayload run(ParentPayload payload) {
        payload = lambda(new Step10_Lambda(), payload);

        payload = nestedSfn(
            new Step20_Nested_InputAdapter(),
            new ChildWorkflow(),
            new Step20_Nested_OutputAdapter(),
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
