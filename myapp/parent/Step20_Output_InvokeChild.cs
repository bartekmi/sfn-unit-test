using sfn_ut.sfn.nested;
using sfn_ut.myapp.child;

namespace sfn_ut.myapp.parent;

public class Step20_Output_InvokeChild : SfnMerge<ParentPayload,ChildPayload> {
  public override ParentPayload Execute(SfnMergeInput<ParentPayload, ChildPayload> input) {
    input.Parent.Step20 = input.Child.FinalAnswer;    
    return input.Parent;
  }
}
