using sfn_ut.sfn.lambda;

namespace sfn_ut.myapp.child;

public class Step20_AddStars : SfnLambda<ChildParams> {
  public override ChildParams Execute(ChildParams input) {
    input.FinalAnswer = "*** " + input.ToUpper + " ***";
    input.ServiceChargeInDollars = 100;
    return input;
  }
}
