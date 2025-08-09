using sfn_ut.sfn.lambda;

namespace sfn_ut.myapp.parent;

public class Step50_LambdaLoop : SfnLambda<ParentPayload> {
  public override ParentPayload Execute(ParentPayload payload) {
    payload.AsyncOpStarted = true;
    return payload;
  }
}
