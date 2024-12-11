using Newtonsoft.Json;

namespace sfn_ut.sfn.parallel;

public class SfnParallel<PAYLOAD> : SfnStepWithPayload<PAYLOAD> {

  public List<SfnStepWithPayload<PAYLOAD>> ParallelFlows { get; set; } = [];
  [JsonIgnore]
  public SfnParallelResultsAssembler<PAYLOAD> ResultsAssembler { get; private set; }

  public SfnParallel(
    List<SfnStepWithPayload<PAYLOAD>> parallelFlows, 
    SfnParallelResultsAssembler<PAYLOAD> resultsAssembler
  ) : base(StepType.Parallel) {
    ParallelFlows = parallelFlows;
    ResultsAssembler = resultsAssembler;
  }

  public override PAYLOAD Execute(PAYLOAD payload) {
    // Though we do it linearly here, there is nothing stopping us from spinning up multiple
    // threads and really doing it in parallel. This would be useful if real dependencies are
    // being invoked.
    List<PAYLOAD> results = [];
    foreach (SfnStepWithPayload<PAYLOAD> flow in ParallelFlows)
      results.Add(flow.Execute(payload));

    return ResultsAssembler.Execute(payload, results);
  }
}