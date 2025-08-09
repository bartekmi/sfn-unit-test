using sfn_ut.sfn;
using sfn_ut.myapp.child;
using sfn_ut.sfn.nested;

namespace sfn_ut.myapp.parent;

public class ParentWorkflow : SfnWorkflow<ParentPayload> {
  public ParentWorkflow() : base(new ErrorHandler()) {
    // Do nothing
  }

  public override ParentPayload Run(ParentPayload payload) {
    payload = Lambda(new Step10_Lambda(), payload);

    payload = NestedSfn(
      new Step20_Nested_InputAdapter(),
      new ChildWorkflow(),
      new Step20_Nested_OutputAdapter(),
      payload);

    payload = Lambda(new Step30_Lambda(), payload);

    payload = Parallel([
        new Step40_Parallel_LambdaA(),
        new Step40_Parallel_LambdaB()
      ],
      new Step40_Parallel_Assember(),
      payload);

    payload = LambdaLoop(
      new Step50_LambdaLoop(),
      new Step50_LambdaLoopGet(),
      payload
    );

    Wait("WaitAtEnd", TimeSpan.FromSeconds(2));

    return payload;
  }
}