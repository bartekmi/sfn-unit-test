using Newtonsoft.Json;
using Newtonsoft.Json.Converters;

using sfn_ut.myapp.parent;
using sfn_ut.myapp.child;
using sfn_ut.sfn;

namespace sfn_ut.myapp_test;

public class ParentStackTest : SfnUnitTest {
  public static void RunTests() {
    RunChild();
    RunParent();
  }

  private static void RunParent() {
    ParentPayload input = new("Bartek");

    SfnWorkflow<ParentPayload> workflow = new ParentWorkflow(); 
    ParentPayload output = workflow.Run(input);

    RunTest("Parent", output, workflow);
  }

  private static void RunChild() {
    ChildPayload input = new("Bartek");

    SfnWorkflow<ChildPayload> workflow = new ChildWorkflow(); 
    ChildPayload output = workflow.Run(input);

    RunTest("Child", output, workflow);
  }

  private static void RunTest<OUTPUT>(string name, OUTPUT output, SfnWorkflow<OUTPUT> workflow) {
    Console.WriteLine();
    Console.WriteLine(">>>>>>>>>>> Running Test: " + name);
    Console.WriteLine();

    var settings = new JsonSerializerSettings {
        Converters = [new StringEnumConverter()],
        Formatting = Formatting.Indented,
        NullValueHandling = NullValueHandling.Ignore,
    };

    string json = JsonConvert.SerializeObject(output, settings);
    Console.WriteLine(json);
    Console.WriteLine();

    string workflowStructure = JsonConvert.SerializeObject(workflow, settings);
    Console.WriteLine(workflowStructure);
  }

}