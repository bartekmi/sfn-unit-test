using sfn_ut.sfn.parallel;

namespace sfn_ut.myapp.parent;

public class Step40_Assember : SfnParallelResultsAssembler<ParentPayload> {
  public override ParentPayload Execute(ParentPayload original, List<ParentPayload> parallelOutputs) {
    original.Step40a = parallelOutputs[0].Step40a;
    original.Step40b = parallelOutputs[1].Step40b;

    return original;
  }
}
