namespace sfn_ut.myapp.parent;

public class ParentPayload(string originalInput) {
  public string OriginalInput { get; set; } = originalInput;
  public string? Step10 { get; set; }
  public string? Step20 { get; set; }
  public string? Step30 { get; set; }
  public string? Step40a { get; set; }
  public string? Step40b { get; set; }
}
