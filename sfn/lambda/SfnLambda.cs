namespace sfn_ut.sfn.lambda;

public abstract class SfnLambda<PAYLOAD> : SfnStepWithPayload<PAYLOAD> {
  public SfnLambda() : base(StepType.Lambda) {
    // Do nothing
  }
}