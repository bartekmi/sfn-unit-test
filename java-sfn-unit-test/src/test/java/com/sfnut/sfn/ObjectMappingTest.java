package com.sfnut.sfn;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
import java.util.Arrays;
import java.util.Map;

public class ObjectMappingTest {
    
    // Test classes based on the example
    public static class Input {
        private String f1;
        private String f2;
        private NestedObject nst;
        
        public Input() {}
        
        public Input(String f1, String f2, NestedObject nst) {
            this.f1 = f1;
            this.f2 = f2;
            this.nst = nst;
        }
        
        // Getters
        public String getF1() { return f1; }
        public String getF2() { return f2; }
        public NestedObject getNst() { return nst; }
        
        // Setters
        public void setF1(String f1) { this.f1 = f1; }
        public void setF2(String f2) { this.f2 = f2; }
        public void setNst(NestedObject nst) { this.nst = nst; }
    }
    
    public static class Output {
        private String o1;
        private int o2;
        
        public Output() {}
        
        // Getters
        public String getO1() { return o1; }
        public int getO2() { return o2; }
        
        // Setters
        public void setO1(String o1) { this.o1 = o1; }
        public void setO2(int o2) { this.o2 = o2; }
        
        @Override
        public String toString() {
            return "Output{o1='" + o1 + "', o2=" + o2 + "}";
        }
    }
    
    public static class NestedObject {
        private int n1;
        
        public NestedObject() {}
        
        public NestedObject(int n1) {
            this.n1 = n1;
        }
        
        // Getter
        public int getN1() { return n1; }
        
        // Setter
        public void setN1(int n1) { this.n1 = n1; }
    }
    
    @Test
    @DisplayName("Test comprehensive object mapping with simple and nested fields")
    public void testComprehensiveObjectMapping() {
        // Create test input
        NestedObject nested = new NestedObject(42);
        Input input = new Input("Hello World", "Unused Field", nested);
        
        // Create object mapping with Map-based field mappings
        ObjectMapping<Input, Output> mapping = new ObjectMapping<>(
            Output.class,
            Map.of(
                "o1", "f1",        // Simple field mapping
                "o2", "nst.n1"     // Nested field mapping
            )
        );
        
        // Perform mapping
        Output output = mapping.map(input);
        
        // Verify results
        assertEquals("Hello World", output.getO1(), "o1 should be mapped from f1");
        assertEquals(42, output.getO2(), "o2 should be mapped from nested n1");
    }
    
    @Test
    @DisplayName("Test null handling for nested objects")
    public void testNullHandling() {
        // Create input with null nested object
        Input input = new Input("Test", "Field", null);
        
        ObjectMapping<Input, Output> mapping = new ObjectMapping<>(
            Output.class,
            Map.of(
                "o1", "f1",        // Simple field mapping should work
                "o2", "nst.n1"     // Nested field mapping should handle null gracefully
            )
        );
        
        Output output = mapping.map(input);
        
        // Verify null handling
        assertEquals("Test", output.getO1(), "o1 should be mapped from f1");
        assertEquals(0, output.getO2(), "o2 should be 0 (default int value when null)");
    }
    
    // Test classes for multi-level nesting
    public static class Level1 {
        private Level2 level2;
        
        public Level1() {}
        public Level1(Level2 level2) { this.level2 = level2; }
        
        public Level2 getLevel2() { return level2; }
        public void setLevel2(Level2 level2) { this.level2 = level2; }
    }
    
    public static class Level2 {
        private Level3 level3;
        
        public Level2() {}
        public Level2(Level3 level3) { this.level3 = level3; }
        
        public Level3 getLevel3() { return level3; }
        public void setLevel3(Level3 level3) { this.level3 = level3; }
    }
    
    public static class Level3 {
        private String value;
        private int number;
        
        public Level3() {}
        public Level3(String value, int number) { 
            this.value = value; 
            this.number = number;
        }
        
        public String getValue() { return value; }
        public void setValue(String value) { this.value = value; }
        
        public int getNumber() { return number; }
        public void setNumber(int number) { this.number = number; }
    }
    
    @Test
    @DisplayName("Test multi-level nested field mapping")
    public void testMultiLevelNesting() {
        // Create deeply nested input
        Level3 level3 = new Level3("Deep Value", 123);
        Level2 level2 = new Level2(level3);
        Level1 input = new Level1(level2);
        
        // Create object mapping with multi-level nesting using Map syntax
        ObjectMapping<Level1, Output> mapping = new ObjectMapping<>(
            Output.class,
            Map.of(
                "o1", "level2.level3.value",
                "o2", "level2.level3.number"
            )
        );
        
        // Perform mapping
        Output output = mapping.map(input);
        
        // Verify results
        assertEquals("Deep Value", output.getO1(), "o1 should be mapped from deeply nested value");
        assertEquals(123, output.getO2(), "o2 should be mapped from deeply nested number");
    }
    
    // Test classes for array/collection support
    public static class CollectionInput {
        private String[] items;
        private List<String> tags;
        
        public CollectionInput() {}
        public CollectionInput(String[] items, List<String> tags) {
            this.items = items;
            this.tags = tags;
        }
        
        public String[] getItems() { return items; }
        public void setItems(String[] items) { this.items = items; }
        
        public List<String> getTags() { return tags; }
        public void setTags(List<String> tags) { this.tags = tags; }
    }
    
    @Test
    @DisplayName("Test array and collection indexing")
    public void testArrayAndCollectionIndexing() {
        // Create input with arrays and collections
        CollectionInput input = new CollectionInput(
            new String[]{"first", "second", "third"},
            Arrays.asList("tag1", "tag2", "tag3")
        );
        
        ObjectMapping<CollectionInput, Output> mapping = new ObjectMapping<>(
            Output.class,
            Map.of(
                "o1", "items[1]"  // Array indexing - should get "second"
            )
        );
        
        Output output = mapping.map(input);
        
        assertEquals("second", output.getO1(), "o1 should be mapped from items[1]");
    }
    
    @Test
    @DisplayName("Test error handling for invalid output class")
    public void testErrorHandling() {
        // Test class without default constructor
        class InvalidOutput {
            private String value;
            
            // No default constructor - this should cause an error
            public InvalidOutput(String value) {
                this.value = value;
            }
            
            public void setValue(String value) { this.value = value; }
        }
        
        Input input = new Input("test", "test", null);
        
        ObjectMapping<Input, InvalidOutput> mapping = new ObjectMapping<>(
            InvalidOutput.class,
            Map.of(
                "value", "f1"
            )
        );
        
        // Should throw RuntimeException due to missing default constructor
        assertThrows(RuntimeException.class, () -> mapping.map(input));
    }
    
    
    @Test
    @DisplayName("Test new Map-based syntax for cleaner field mapping")
    public void testMapBasedSyntax() {
        // Create test input
        NestedObject nested = new NestedObject(99);
        Input input = new Input("Map Syntax Test", "Unused", nested);
        
        // Create object mapping using the new cleaner Map.of() syntax
        ObjectMapping<Input, Output> mapping = new ObjectMapping<>(
            Output.class,
            Map.of(
                "o1", "f1",           // Simple field mapping
                "o2", "nst.n1"        // Nested field mapping
            )
        );
        
        // Perform mapping
        Output output = mapping.map(input);
        
        // Verify results
        assertEquals("Map Syntax Test", output.getO1(), "o1 should be mapped from f1");
        assertEquals(99, output.getO2(), "o2 should be mapped from nested n1");
    }
    
    @Test
    @DisplayName("Test Map-based syntax with complex nesting and indexing")
    public void testMapBasedSyntaxComplex() {
        // Create deeply nested input with collections
        Level3 level3 = new Level3("Complex Test", 456);
        Level2 level2 = new Level2(level3);
        Level1 level1Input = new Level1(level2);
        
        CollectionInput collectionInput = new CollectionInput(
            new String[]{"zero", "one", "two"},
            Arrays.asList("tagA", "tagB", "tagC")
        );
        
        // Test multi-level nesting with Map syntax
        ObjectMapping<Level1, Output> nestedMapping = new ObjectMapping<>(
            Output.class,
            Map.of(
                "o1", "level2.level3.value",
                "o2", "level2.level3.number"
            )
        );
        
        // Test array indexing with Map syntax
        ObjectMapping<CollectionInput, Output> indexMapping = new ObjectMapping<>(
            Output.class,
            Map.of(
                "o1", "items[2]"  // Should get "two"
            )
        );
        
        // Verify nested mapping
        Output nestedOutput = nestedMapping.map(level1Input);
        assertEquals("Complex Test", nestedOutput.getO1());
        assertEquals(456, nestedOutput.getO2());
        
        // Verify indexed mapping
        Output indexedOutput = indexMapping.map(collectionInput);
        assertEquals("two", indexedOutput.getO1());
    }
}
