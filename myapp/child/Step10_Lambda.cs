using sfn_ut.sfn.lambda;

namespace sfn_ut.myapp.child;

public class Step10_Lambda : SfnLambda<ChildPayload> {
  public override ChildPayload Execute(ChildPayload input) {
    input.HappyPathData += " > Child 10";
    return input;
  }
}
