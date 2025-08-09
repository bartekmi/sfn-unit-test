namespace sfn_ut.myapp.parent;

public class ParentPayload(string originalInput) {
  public string HappyPathData { get; set; } = originalInput;
  public int? DataFromChild { get; set;  }
  public string? ParallelA { get; set; }
  public string? ParallelB { get; set; }

  public bool AsyncOpStarted { get; set; }
  public int LambdaLoopIteration { get; set; }
}
