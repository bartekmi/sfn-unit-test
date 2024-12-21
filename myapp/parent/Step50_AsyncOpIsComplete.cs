using sfn_ut.sfn.await;

namespace sfn_ut.myapp.parent;

public class Step50_AsyncOpIsComplete : SfnIsComplete<ParentPayload> {
  public override SfnIsCompleteOutput<ParentPayload> IsComplete(ParentPayload payload) {
    return new SfnIsCompleteOutput<ParentPayload>(payload, true);
  }
}
