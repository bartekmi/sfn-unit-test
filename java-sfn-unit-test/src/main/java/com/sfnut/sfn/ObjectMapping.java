package com.sfnut.sfn;

import java.util.List;

public class ObjectMapping<INPUT, OUTPUT> {
    private final List<FieldMapping<OUTPUT, INPUT>> fieldMappings;
    private final Class<OUTPUT> outputClass;

    public ObjectMapping(Class<OUTPUT> outputClass, List<FieldMapping<OUTPUT, INPUT>> fieldMappings) {
        this.outputClass = outputClass;
        this.fieldMappings = fieldMappings;
    }

    public OUTPUT map(INPUT input) {
        try {
            // Create new instance of output class
            OUTPUT output = outputClass.getDeclaredConstructor().newInstance();
            
            // Apply all field mappings
            for (FieldMapping<OUTPUT, INPUT> mapping : fieldMappings) {
                mapping.apply(output, input);
            }
            
            return output;
        } catch (Exception e) {
            throw new RuntimeException("Failed to create and map object of type " + outputClass.getName(), e);
        }
    }
}
