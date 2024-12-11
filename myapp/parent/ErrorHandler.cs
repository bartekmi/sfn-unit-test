using sfn_ut.sfn;
using sfn_ut.sfn.error;

namespace sfn_ut.myapp.parent;

public class ErrorHandler : SfnErrorHandler<ParentPayload> {
  public override SfnErrorHandlerInput<ParentPayload> Execute(SfnErrorHandlerInput<ParentPayload> payload) {
    return payload;
  }
}
