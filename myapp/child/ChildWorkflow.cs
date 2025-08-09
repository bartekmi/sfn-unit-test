using sfn_ut.sfn;

namespace sfn_ut.myapp.child;

public class ChildWorkflow : SfnWorkflow<ChildPayload> {
  public ChildWorkflow() : base(new ErrorHandler()) {
    // Do nothing
  }

  public override ChildPayload Run(ChildPayload payload) {
    payload = Lambda(new Step10_Lambda(), payload);
    payload = Lambda(new Step20_Lambda(), payload);

    return payload;
  }
}