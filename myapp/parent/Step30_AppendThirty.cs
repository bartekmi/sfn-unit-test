using sfn_ut.sfn.lambda;

namespace sfn_ut.myapp.parent;

public class Step30_AppendThirty : SfnLambda<ParentParams> {
  public override ParentParams Execute(ParentParams payload) {
    payload.Step2_output = payload.ChildOutput + " Thirty";
    return payload;
  }
}
