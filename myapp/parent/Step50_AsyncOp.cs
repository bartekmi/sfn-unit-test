using sfn_ut.sfn.lambda;

namespace sfn_ut.myapp.parent;

public class Step50_AsyncOp : SfnLambda<ParentPayload> {
  public override ParentPayload Execute(ParentPayload payload) {
    payload.AsyncOpStarted = "Async Operation has started";
    return payload;
  }
}
