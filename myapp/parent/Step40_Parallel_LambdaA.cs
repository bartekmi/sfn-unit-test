using sfn_ut.sfn.lambda;

namespace sfn_ut.myapp.parent;

public class Step40_Parallel_LambdaA : SfnLambda<ParentPayload> {
  public override ParentPayload Execute(ParentPayload payload) {
    payload.ParallelA = "Foo";
    return payload;
  }
}
