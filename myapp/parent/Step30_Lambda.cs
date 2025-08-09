using sfn_ut.sfn.lambda;

namespace sfn_ut.myapp.parent;

public class Step30_Lambda : SfnLambda<ParentPayload> {
  public override ParentPayload Execute(ParentPayload payload) {
    payload.HappyPathData += " > 30";
    return payload;
  }
}
