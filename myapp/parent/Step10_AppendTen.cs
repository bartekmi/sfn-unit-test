using sfn_ut.sfn.lambda;

namespace sfn_ut.myapp.parent;

public class Step10_AppendTen : SfnLambda<ParentParams> {
  public override ParentParams Execute(ParentParams payload) {
    payload.Step1_Output = payload.OriginalInput + " Ten";
    return payload;
  }
}
