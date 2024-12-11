using sfn_ut.sfn.lambda;

namespace sfn_ut.myapp.child;

public class Step10_AppendTen : SfnLambda<ChildPayload> {
  public override ChildPayload Execute(ChildPayload input) {
    input.ToUpper = input.FromParent.ToUpper();
    return input;
  }
}
