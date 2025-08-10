package com.sfnut.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.sfnut.SfnUnitTest;
import com.sfnut.myapp.parent.NestedObject;
import com.sfnut.myapp.parent.ParentPayload;
import com.sfnut.myapp.parent.ParentWorkflow;
import com.sfnut.sfn.SfnWorkflow;

public class ParentStackTest extends SfnUnitTest {
    
    public static void runTests() throws Exception {
        runParent();
    }

    private static void runParent() throws Exception {
        ParentPayload input = new ParentPayload("Start",
          new NestedObject("NESTED"));

        SfnWorkflow<ParentPayload> workflow = new ParentWorkflow();
        ParentPayload output = workflow.run(input);

        String actual = runTest("Parent", output, workflow);

        String expected = """
{
  "HappyPathData" : "Start > 10 > Child 10 > Child 20 > 30",
  "DataFromChild" : "***NESTED***",
  "ParallelA" : "Foo",
  "ParallelB" : "Bar",
  "AsyncOpStarted" : true,
  "LambdaLoopIteration" : 5,
  "NestedObject" : {
    "NestedField" : "NESTED"
  }
}            
            """;

        if (!expected.trim().equals(actual.trim())) {
          throw new Exception("Did not mtach: " + expected);
        }
    }

    private static <OUTPUT> String runTest(String name, OUTPUT workflowOutput, SfnWorkflow<OUTPUT> workflow) 
      throws Exception {
        System.out.println();
        System.out.println(">>>>>>>>>>> Running Test: " + name);
        System.out.println();

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.enable(SerializationFeature.INDENT_OUTPUT);

        String workflowStructure = mapper.writeValueAsString(workflow);
        System.out.println(workflowStructure);

        String workflowOutputJson = mapper.writeValueAsString(workflowOutput);
        System.out.println(workflowOutputJson);
        System.out.println();

        return workflowOutputJson;
    }
}
