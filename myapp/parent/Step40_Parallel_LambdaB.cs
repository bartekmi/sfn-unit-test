using sfn_ut.sfn.lambda;

namespace sfn_ut.myapp.parent;

public class Step40_Parallel_LambdaB : SfnLambda<ParentPayload> {
  public override ParentPayload Execute(ParentPayload payload) {
    payload.ParallelB = "Bar";
    return payload;
  }
}
