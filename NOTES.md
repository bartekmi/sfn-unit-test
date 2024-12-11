Running Notes:

Next Steps:
* Consider moving the concept of "wrapping" from nested workflow invocation to any SfnStep
* Create an intermediate class or interface which is a SfnStep which has only a single type for both input and output - e.g. SfnStepWithPayload.
  and a "PAYLOAD Execute(PAYLOAD input)" abstract method
* Then, "parallel" takes a list of these, while base class of Workflow can have multiple SfnStep types from which CDK is generated.