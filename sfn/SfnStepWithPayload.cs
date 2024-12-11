namespace sfn_ut.sfn;

public abstract class SfnStepWithPayload<PAYLOAD>(StepType type) : SfnStep(type) {
  public abstract PAYLOAD Execute(PAYLOAD input);
}