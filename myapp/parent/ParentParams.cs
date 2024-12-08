using sfn_ut.sfn;

namespace sfn_ut.myapp.parent;

public class ParentParams(string originalInput) {
  public string OriginalInput { get; set; } = originalInput;
  public string? Step1_Output { get; set; }
  public string? ChildOutput { get; set; }
  public string? Step2_output { get; set; }
}
