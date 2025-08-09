namespace sfn_ut.sfn.await;

public class SfnIsCompleteOutput<PAYLOAD>(PAYLOAD payload, bool isComplete) {
  public PAYLOAD Payload { get; set; } = payload;
  public bool IsComplete { get; set; } = isComplete;
}