package com.sfnut.sfn;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
import java.util.Arrays;

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
        
        // Create object mapping with string-based field mappings
        ObjectMapping<Input, Output> mapping = new ObjectMapping<>(
            Output.class,
            List.of(
                // Simple field mapping
                new FieldMapping<>("o1", "f1"),
                // Nested field mapping
                new FieldMapping<>("o2", "nst.n1")
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
            List.of(
                // Simple field mapping should work
                new FieldMapping<>("o1", "f1"),
                // Nested field mapping should handle null gracefully
                new FieldMapping<>("o2", "nst.n1")
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
        
        // Create object mapping with multi-level nesting using string paths
        ObjectMapping<Level1, Output> mapping = new ObjectMapping<>(
            Output.class,
            List.of(
                new FieldMapping<>("o1", "level2.level3.value"),
                new FieldMapping<>("o2", "level2.level3.number")
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
            List.of(
                // Array indexing
                new FieldMapping<>("o1", "items[1]")  // "second"
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
            List.of(
                new FieldMapping<>("value", "f1")
            )
        );
        
        // Should throw RuntimeException due to missing default constructor
        assertThrows(RuntimeException.class, () -> mapping.map(input));
    }
    
    @Test
    @DisplayName("Test path segment extraction for code generation")
    public void testPathSegmentExtraction() {
        // Test that we can extract path information for code generation
        FieldMapping<Output, Input> simpleMapping = new FieldMapping<>("o1", "f1");
        FieldMapping<Output, Input> nestedMapping = new FieldMapping<>("o2", "nst.n1");
        
        // Test simple path
        assertEquals("o1", simpleMapping.getOutputProperty());
        assertEquals("f1", simpleMapping.getPropertyPath());
        assertEquals(List.of("f1"), simpleMapping.getPathSegmentNames());
        
        // Test nested path
        assertEquals("o2", nestedMapping.getOutputProperty());
        assertEquals("nst.n1", nestedMapping.getPropertyPath());
        assertEquals(List.of("nst", "n1"), nestedMapping.getPathSegmentNames());
        
        // Test path segments
        List<FieldMapping.PathSegment> segments = nestedMapping.getPathSegments();
        assertEquals(2, segments.size());
        assertEquals("nst", segments.get(0).getPropertyName());
        assertEquals("n1", segments.get(1).getPropertyName());
        assertFalse(segments.get(0).isIndexed());
        assertFalse(segments.get(1).isIndexed());
    }
    
    @Test
    @DisplayName("Test indexed path segment extraction")
    public void testIndexedPathSegmentExtraction() {
        FieldMapping<Output, CollectionInput> indexedMapping = new FieldMapping<>("o1", "items[2]");
        
        assertEquals("items[2]", indexedMapping.getPropertyPath());
        assertEquals(List.of("items"), indexedMapping.getPathSegmentNames());
        
        List<FieldMapping.PathSegment> segments = indexedMapping.getPathSegments();
        assertEquals(1, segments.size());
        assertEquals("items", segments.get(0).getPropertyName());
        assertTrue(segments.get(0).isIndexed());
        assertEquals(Integer.valueOf(2), segments.get(0).getIndex());
    }
}
