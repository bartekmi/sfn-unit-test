namespace sfn_ut.sfn.nested;

public abstract class SfnMerge<PARENT,CHILD> : SfnStep {
  public abstract PARENT Execute(SfnMergeInput<PARENT,CHILD> input);

  public SfnMerge() : base(StepType.Merge) {
    // Do nothing
  }
}