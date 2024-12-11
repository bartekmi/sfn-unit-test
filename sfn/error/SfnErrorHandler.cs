using sfn_ut.sfn.error;

namespace sfn_ut.sfn;

public abstract class SfnErrorHandler<PAYLOAD> : SfnStep {
  public abstract SfnErrorHandlerInput<PAYLOAD> Execute(SfnErrorHandlerInput<PAYLOAD> payload);

  public SfnErrorHandler() : base(StepType.Error) {
    // Do nothing
  }
}