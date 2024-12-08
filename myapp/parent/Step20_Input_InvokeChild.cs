using sfn_ut.sfn.nested;
using sfn_ut.myapp.child;

namespace sfn_ut.myapp.parent;

public class Step20_Input_InvokeChild : SfnTransform<ParentParams, ChildParams> {
  public override ChildParams Execute(ParentParams input) {
    return new ChildParams(input.Step1_Output!);
  }
}

