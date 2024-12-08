using sfn_ut.sfn.lambda;

namespace sfn_ut.myapp.child;

public class Step10_AppendTen : SfnLambda<ChildParams> {
  public override ChildParams Execute(ChildParams input) {
    input.ToUpper = input.FromParent.ToUpper();
    return input;
  }
}
