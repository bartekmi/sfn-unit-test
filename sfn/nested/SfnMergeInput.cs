namespace sfn_ut.sfn.nested;

public class SfnMergeInput<PARENT,CHILD>(PARENT parent, CHILD child) {
  public PARENT Parent { get; set; } = parent;
  public CHILD Child { get; set; } = child;
}