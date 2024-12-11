using sfn_ut.sfn.nested;
using sfn_ut.myapp.child;

namespace sfn_ut.myapp.parent;

public class Step20_Input_InvokeChild : SfnTransform<ParentPayload, ChildPayload> {
  public override ChildPayload Execute(ParentPayload input) {
    return new ChildPayload(input.Step10!);
  }
}

