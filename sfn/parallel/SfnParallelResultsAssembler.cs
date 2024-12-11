namespace sfn_ut.sfn.parallel;

public abstract class SfnParallelResultsAssembler<PAYLOAD> : SfnStep {
  public abstract PAYLOAD Execute(PAYLOAD original, List<PAYLOAD> parallelOutputs);

  public SfnParallelResultsAssembler() : base(StepType.ParallelResultsAssembler) {
    // Do nothing
  }
}