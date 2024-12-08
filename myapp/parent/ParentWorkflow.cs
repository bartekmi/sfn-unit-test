using sfn_ut.sfn;
using sfn_ut.myapp.child;

namespace sfn_ut.myapp.parent;

public class ParentWorkflow : SfnWorkflow<ParentParams> {
  public ParentWorkflow() : base(new ErrorHandler()) {
    // Do nothing
  }

  public override ParentParams Run(ParentParams payload) {
    payload = Lambda(new Step10_AppendTen(), payload);

    payload = NestedSfn(
      new Step20_Input_InvokeChild(),
      new ChildWorkflow(), 
      new Step20_Output_InvokeChild(),
      payload);
    
    payload = Lambda(new Step30_AppendThirty(), payload);

    return payload;
  }
}