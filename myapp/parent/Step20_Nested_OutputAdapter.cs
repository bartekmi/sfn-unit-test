using sfn_ut.sfn.nested;
using sfn_ut.myapp.child;

namespace sfn_ut.myapp.parent;

public class Step20_Nested_OutputAdapter : SfnMerge<ParentPayload, ChildPayload> {
  public override ParentPayload Execute(SfnMergeInput<ParentPayload, ChildPayload> input) {
    input.Parent.HappyPathData = input.Child.HappyPathData;
    input.Parent.DataFromChild = input.Child.ServiceChargeInDollars;

    return input.Parent;
  }
}
