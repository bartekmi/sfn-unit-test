using sfn_ut.sfn.await;

namespace sfn_ut.sfn;

public abstract class SfnIsComplete<PAYLOAD> : SfnStep {
  public abstract SfnIsCompleteOutput<PAYLOAD> IsComplete(PAYLOAD payload);
}