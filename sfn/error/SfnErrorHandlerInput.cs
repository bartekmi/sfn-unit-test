namespace sfn_ut.sfn.error;

public class SfnErrorHandlerInput<PAYLOAD>(PAYLOAD payload, string errorMessage) {
  public PAYLOAD Payload { get; set; } = payload;
  public string ErrorMessage { get; set; } = errorMessage;
}