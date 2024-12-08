using sfn_ut.sfn;

namespace sfn_ut.myapp.child;

public class ChildWorkflow : SfnWorkflow<ChildParams> {
  public ChildWorkflow() : base(new ErrorHandler()) {
    // Do nothing
  }

  public override ChildParams Run(ChildParams payload) {
    payload = Lambda(new Step10_AppendTen(), payload);
    payload = Lambda(new Step20_AddStars(), payload);
    
    return payload;
  }
}