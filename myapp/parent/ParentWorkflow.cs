using sfn_ut.sfn;
using sfn_ut.myapp.child;
using sfn_ut.sfn.nested;

namespace sfn_ut.myapp.parent;

public class ParentWorkflow : SfnWorkflow<ParentPayload> {
  public ParentWorkflow() : base(new ErrorHandler()) {
    // Do nothing
  }

  public override ParentPayload Run(ParentPayload payload) {
    payload = Lambda(new Step10_AppendTen(), payload);

    payload = NestedSfn(
      new Step20_Input_InvokeChild(),
      new ChildWorkflow(), 
      new Step20_Output_InvokeChild(),
      payload);
    
    payload = Lambda(new Step30_AppendThirty(), payload);

    payload = Parallel([ new Step40a_Foo(), new Step40b_Bar() ], 
      new Step40_Assember(),
      payload);

    return payload;
  }
}