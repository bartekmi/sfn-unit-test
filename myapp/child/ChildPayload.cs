namespace sfn_ut.myapp.child;

public class ChildPayload(string fromParent) {
  public string FromParent { get; set; } = fromParent;
  public string? ToUpper { get; set; }
  public string? FinalAnswer { get; set; }
  public int ServiceChargeInDollars { get; set; }
}
