using sfn_ut.sfn.lambda;

namespace sfn_ut.sfn.await;

public class SfnLambdaWithLoop<PAYLOAD>(
  SfnLambda<PAYLOAD> lambda,
  SfnIsComplete<PAYLOAD> isComplete
) : SfnStepWithPayload<PAYLOAD>(StepType.LambdaLoop) {

  public SfnLambda<PAYLOAD> Lambda { get; set; } = lambda;
  public SfnIsComplete<PAYLOAD> IsComplete { get; set; } = isComplete;
  public int MaxChecks { get; set; } = 10;

  public override PAYLOAD Execute(PAYLOAD payload) {
    payload = Lambda.Execute(payload);

    for (int ii = 0; ii < MaxChecks; ii++) {
      if (IsComplete.IsComplete(payload).IsComplete)
        return payload;
    }

    throw new Exception(string.Format("Exceeded maximum number of checks allowed ({0})",
      MaxChecks));
  }
}