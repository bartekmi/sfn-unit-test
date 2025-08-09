using sfn_ut.sfn.lambda;

namespace sfn_ut.myapp.child;

public class Step20_Lambda : SfnLambda<ChildPayload> {
  public override ChildPayload Execute(ChildPayload input) {
    input.HappyPathData += " > Child 20";
    input.ServiceChargeInDollars = 100;
    return input;
  }
}
