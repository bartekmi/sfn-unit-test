using sfn_ut.sfn.lambda;

namespace sfn_ut.myapp.parent;

public class Step30_AppendThirty : SfnLambda<ParentPayload> {
  public override ParentPayload Execute(ParentPayload payload) {
    payload.Step30 = payload.Step20 + " Thirty";
    return payload;
  }
}
