using sfn_ut.sfn.lambda;
using sfn_ut.sfn.nested;
using sfn_ut.sfn.await;
using sfn_ut.sfn.error;

namespace sfn_ut.sfn;

public enum Mode {
  Test,
  ExtractMetadata
}

public abstract class SfnWorkflow<PARENT>(SfnErrorHandler<PARENT> errorHandler) : SfnStep {
  public List<SfnStep> Steps { get; set; } = [];
  public SfnErrorHandler<PARENT> ErrorHandler { get; set; } = errorHandler;

  public abstract PARENT Run(PARENT input);

  private void HandleErrorInternal(PARENT payload, Exception e) {
    // Do logging, assertions, etc
    ErrorHandler.Execute(new SfnErrorHandlerInput<PARENT>(payload, e.Message));
  }

  public PARENT Lambda(SfnLambda<PARENT> lambda, PARENT input) {
    Steps.Add(lambda);

    try {
      return lambda.Execute(input);
    } catch (Exception e) {
      HandleErrorInternal(input, e);
      throw;
    }
  }

  public PARENT LambdaWithAwait(SfnLambdaWithAwait<PARENT> lambdaWithAwait, PARENT payload) {
    Steps.Add(lambdaWithAwait);

    try {
      return lambdaWithAwait.Execute(payload);
    } catch (Exception e) {
      HandleErrorInternal(payload, e);
      throw;
    }
  }

  public PARENT NestedSfn<CHILD>(
    SfnTransform<PARENT, CHILD> transformInput,
    SfnWorkflow<CHILD> sfn, 
    SfnMerge<PARENT,CHILD> mergeOutput,
    PARENT input) {

    try {
      // Transform payload of this Workflow to what is needed by the Child
      Steps.Add(transformInput);
      CHILD childIn = transformInput.Execute(input);

      Steps.Add(sfn);
      CHILD childOut = sfn.Run(childIn);

      // Merge Child output payload back into the Parent payload
      Steps.Add(mergeOutput);
      return mergeOutput.Execute(new(input, childOut));
    } catch (Exception e) {
      HandleErrorInternal(input, e);
      throw;
    }
  }
}