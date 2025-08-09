using sfn_ut.sfn.lambda;
using sfn_ut.sfn.wait;
using sfn_ut.sfn.nested;
using sfn_ut.sfn.await;
using sfn_ut.sfn.error;
using sfn_ut.sfn.parallel;

namespace sfn_ut.sfn;

public enum Mode {
  Test,
  ExtractMetadata
}

public abstract class SfnWorkflow<PAYLOAD> {
  public List<SfnStep> Steps { get; set; } = [];
  public SfnErrorHandler<PAYLOAD> ErrorHandler { get; set; }
  public string WorkflowName { get; private set; }

  public abstract PAYLOAD Run(PAYLOAD input);

  public SfnWorkflow(SfnErrorHandler<PAYLOAD> errorHandler) {
    ErrorHandler = errorHandler;
    WorkflowName = GetType().FullName!;
  }

  internal void HandleErrorInternal(PAYLOAD payload, Exception e) {
    // Do logging, assertions, etc
    ErrorHandler.Execute(new SfnErrorHandlerInput<PAYLOAD>(payload, e.Message));
  }

  public PAYLOAD Lambda(SfnLambda<PAYLOAD> lambda, PAYLOAD input) {
    Steps.Add(lambda);

    try {
      return lambda.Execute(input);
    }
    catch (Exception e) {
      HandleErrorInternal(input, e);
      throw;
    }
  }

  public void Wait(string stepName, TimeSpan duration) {
    Steps.Add(new SfnWait<PAYLOAD>(stepName, duration));
  }

  public PAYLOAD LambdaLoop(
    SfnLambda<PAYLOAD> lambda,
    SfnIsComplete<PAYLOAD> isComplete,
    PAYLOAD payload
    ) {
    SfnLambdaWithLoop<PAYLOAD> lambdaWithAwait = new(lambda, isComplete);
    Steps.Add(lambdaWithAwait);

    try {
      return lambdaWithAwait.Execute(payload);
    }
    catch (Exception e) {
      HandleErrorInternal(payload, e);
      throw;
    }
  }

  public PAYLOAD Parallel(List<SfnStepWithPayload<PAYLOAD>> steps, SfnParallelResultsAssembler<PAYLOAD> assembler, PAYLOAD payload) {
    SfnParallel<PAYLOAD> parallel = new(steps, assembler);

    Steps.Add(parallel);
    Steps.Add(assembler);

    try {
      return parallel.Execute(payload);
    }
    catch (Exception e) {
      HandleErrorInternal(payload, e);
      throw;
    }
  }

  public PAYLOAD NestedSfn<CHILD>(
    SfnTransform<PAYLOAD, CHILD> transform,
    SfnWorkflow<CHILD> nested,
    SfnMerge<PAYLOAD, CHILD> merge,
    PAYLOAD payload) {

    SfnWorkflowInvocation<PAYLOAD, CHILD> nestedInvocation = new(transform, nested, merge);

    Steps.Add(transform);
    Steps.Add(nestedInvocation);
    Steps.Add(merge);

    try {
      return nestedInvocation.Execute(payload);
    }
    catch (Exception e) {
      HandleErrorInternal(payload, e);
      throw;
    }
  }
}
