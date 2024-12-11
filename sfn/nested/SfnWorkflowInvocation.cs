using Newtonsoft.Json;

namespace sfn_ut.sfn.nested;

public class SfnWorkflowInvocation<PARENT,CHILD>(
  SfnTransform<PARENT, CHILD> transformInput,
  SfnWorkflow<CHILD> nested,
  SfnMerge<PARENT, CHILD> mergeOutput
  ) : SfnStepWithPayload<PARENT>(StepType.InvokeNested) {

  [JsonIgnore]
  public SfnTransform<PARENT, CHILD> TransformInput { get; private set; } = transformInput;
  [JsonIgnore]
  public SfnWorkflow<CHILD> Nested { get; private set; } = nested;
  [JsonIgnore]
  public SfnMerge<PARENT, CHILD> MergeOutput { get; private set; } = mergeOutput;

  public string NestedWorkflowName { get; private set; } = nested.WorkflowName;

  public override PARENT Execute(PARENT payload) {
    CHILD childIn = TransformInput.Execute(payload);
    CHILD childOut = Nested.Run(childIn);
    return MergeOutput.Execute(new(payload, childOut));
  }
}