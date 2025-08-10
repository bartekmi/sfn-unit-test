package com.sfnut.sfn;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

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
    @DisplayName("Test basic object mapping with simple and nested fields")
    public void testBasicObjectMapping() {
        // Create test input
        NestedObject nested = new NestedObject(42);
        Input input = new Input("Hello World", "Unused Field", nested);
        
        // Create object mapping
        ObjectMapping<Input, Output> mapping = new ObjectMapping<>(
            Output.class,
            List.of(
                new FieldMapping<>(Output::setO1, Input::getF1),
                new FieldMapping<>(Output::setO2, Input::getNst, NestedObject::getN1)
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
                new FieldMapping<>(Output::setO1, Input::getF1),
                new FieldMapping<>(Output::setO2, Input::getNst, NestedObject::getN1)
            )
        );
        
        Output output = mapping.map(input);
        
        // Verify null handling
        assertEquals("Test", output.getO1(), "o1 should be mapped from f1");
        assertEquals(0, output.getO2(), "o2 should be 0 (default int value when null)");
    }
    
    // Static inner classes for the multiple fields test
    public static class MultiInput {
        private String field1;
        private String field2;
        
        public MultiInput(String field1, String field2) {
            this.field1 = field1;
            this.field2 = field2;
        }
        
        public String getField1() { return field1; }
        public String getField2() { return field2; }
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
                new FieldMapping<>(InvalidOutput::setValue, Input::getF1)
            )
        );
        
        // Should throw RuntimeException due to missing default constructor
        assertThrows(RuntimeException.class, () -> mapping.map(input));
    }
}
