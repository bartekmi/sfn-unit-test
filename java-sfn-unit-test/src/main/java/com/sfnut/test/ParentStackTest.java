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

        runTest("Parent", output, workflow);
    }

    private static <OUTPUT> void runTest(String name, OUTPUT output, SfnWorkflow<OUTPUT> workflow) 
      throws Exception {
        System.out.println();
        System.out.println(">>>>>>>>>>> Running Test: " + name);
        System.out.println();

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.enable(SerializationFeature.INDENT_OUTPUT);

        String json = mapper.writeValueAsString(output);
        System.out.println(json);
        System.out.println();

        String workflowStructure = mapper.writeValueAsString(workflow);
        System.out.println(workflowStructure);
    }
}
