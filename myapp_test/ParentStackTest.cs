using Newtonsoft.Json;
using sfn_ut.myapp.parent;

namespace sfn_ut.myapp_test;

public class ParentStackTest : SfnUnitTest {
  public static void RunTest() {
    ParentParams input = new("Bartek");
    ParentParams output = new ParentWorkflow().Run(input);

    string json = JsonConvert.SerializeObject(output, Formatting.Indented);
    Console.WriteLine(json);
  }

}