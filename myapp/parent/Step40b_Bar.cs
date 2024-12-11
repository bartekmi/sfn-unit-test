using sfn_ut.sfn.lambda;

namespace sfn_ut.myapp.parent;

public class Step40b_Bar : SfnLambda<ParentPayload> {
  public override ParentPayload Execute(ParentPayload payload) {
    payload.Step40b = "Bar";
    return payload;
  }
}
