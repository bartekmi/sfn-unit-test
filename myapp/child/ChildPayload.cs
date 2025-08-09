namespace sfn_ut.myapp.child;

public class ChildPayload(string fromParent) {
  public string HappyPathData { get; set; } = fromParent;
  public int ServiceChargeInDollars { get; set; }
}
