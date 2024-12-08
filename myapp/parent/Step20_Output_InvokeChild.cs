using sfn_ut.sfn.nested;
using sfn_ut.myapp.child;

namespace sfn_ut.myapp.parent;

public class Step20_Output_InvokeChild : SfnMerge<ParentParams,ChildParams> {
  public override ParentParams Execute(SfnMergeInput<ParentParams, ChildParams> input) {
    input.Parent.ChildOutput = input.Child.FinalAnswer;    
    return input.Parent;
  }
}
