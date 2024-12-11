using sfn_ut.sfn;
using sfn_ut.sfn.error;

namespace sfn_ut.myapp.child;

public class ErrorHandler : SfnErrorHandler<ChildPayload> {
  public override SfnErrorHandlerInput<ChildPayload> Execute(SfnErrorHandlerInput<ChildPayload> payload) {
    return payload;
  }
}
