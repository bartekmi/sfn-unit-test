namespace sfn_ut.sfn.wait;

public class SfnWait<PAYLOAD>(string stepName, TimeSpan duration) 
  : SfnStepWithPayload<PAYLOAD>(StepType.Wait) {
    
  public readonly string StepName = stepName;
  public readonly TimeSpan Duration = duration;

  public override PAYLOAD Execute(PAYLOAD input) {
    // TODO: Increase fake time
    return input;
  }
}