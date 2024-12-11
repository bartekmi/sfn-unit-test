using sfn_ut.sfn.lambda;

namespace sfn_ut.myapp.parent;

public class Step40a_Foo : SfnLambda<ParentPayload> {
  public override ParentPayload Execute(ParentPayload payload) {
    payload.Step40a = "Foo";
    return payload;
  }
}
