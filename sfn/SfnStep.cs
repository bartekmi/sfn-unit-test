namespace sfn_ut.sfn;

public enum StepType {
  Lambda,
  Wait,
  Error,

  // Related to Lambda-Loop
  LambdaLoop,
  LambdaLoopIsComplete,

  // Related to InvokeNested
  InvokeNested,
  InputAdapter,
  OutputAdapter,

  // Related to Parallel
  Parallel,
  ParallelResultsAssembler,
}

public abstract class SfnStep {
  public StepType Type { get; set; }
  public string? ClassName { get; set; }

  protected SfnStep(StepType type) {
    Type = type;
    if (!GetType().IsGenericType)
      ClassName = GetType().FullName!;
  }
}