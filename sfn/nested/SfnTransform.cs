namespace sfn_ut.sfn.nested;

public abstract class SfnTransform<INPUT,OUTPUT> : SfnStep {
  public abstract OUTPUT Execute(INPUT input);
}