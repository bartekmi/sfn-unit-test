namespace sfn_ut.sfn.await;

public abstract class SfnIsComplete<PAYLOAD> : SfnStep {
  public abstract SfnIsCompleteOutput<PAYLOAD> IsComplete(PAYLOAD payload);

  public SfnIsComplete() : base(StepType.IsComplete) {
    // Do nothing
  }
}