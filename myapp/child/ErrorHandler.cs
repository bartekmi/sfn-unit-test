using sfn_ut.sfn;
using sfn_ut.sfn.error;

namespace sfn_ut.myapp.child;

public class ErrorHandler : SfnErrorHandler<ChildParams> {
  public override SfnErrorHandlerInput<ChildParams> Execute(SfnErrorHandlerInput<ChildParams> payload) {
    return payload;
  }
}
