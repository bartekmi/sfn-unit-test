using sfn_ut.sfn.parallel;

namespace sfn_ut.myapp.parent;

public class Step40_Parallel_Assember : SfnParallelResultsAssembler<ParentPayload> {
  public override ParentPayload Execute(ParentPayload original, List<ParentPayload> parallelOutputs) {
    original.ParallelA = parallelOutputs[0].ParallelA;
    original.ParallelB = parallelOutputs[1].ParallelB;

    return original;
  }
}
