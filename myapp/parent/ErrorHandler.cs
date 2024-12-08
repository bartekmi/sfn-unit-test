using sfn_ut.sfn;
using sfn_ut.sfn.error;

namespace sfn_ut.myapp.parent;

public class ErrorHandler : SfnErrorHandler<ParentParams> {
  public override SfnErrorHandlerInput<ParentParams> Execute(SfnErrorHandlerInput<ParentParams> payload) {
    return payload;
  }
}
