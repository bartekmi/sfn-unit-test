namespace sfn_ut.sfn.lambda;

public abstract class SfnLambda<PAYLOAD> : SfnStep {
  public abstract PAYLOAD Execute(PAYLOAD payload);
}