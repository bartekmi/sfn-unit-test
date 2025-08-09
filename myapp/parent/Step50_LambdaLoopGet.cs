using sfn_ut.sfn.await;

namespace sfn_ut.myapp.parent;

public class Step50_LambdaLoopGet : SfnIsComplete<ParentPayload> {
  public override SfnIsCompleteOutput<ParentPayload> IsComplete(ParentPayload payload) {
    bool isComplete = false;
    
    if (payload.LambdaLoopIteration >= 5)
      isComplete = true;
    else
      payload.LambdaLoopIteration += 1;

    return new SfnIsCompleteOutput<ParentPayload>(payload, isComplete);
  }
}
