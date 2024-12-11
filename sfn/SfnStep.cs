using sfn_ut.sfn.nested;

namespace sfn_ut.sfn;

public enum StepType {
  Lambda,
  Error,

  // Related to Await
  Await,
  IsComplete,

  // Related to InvokeNested
  InvokeNested,
  Transform,
  Merge,

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