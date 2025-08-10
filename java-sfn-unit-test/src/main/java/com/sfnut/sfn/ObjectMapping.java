package com.sfnut.sfn;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ObjectMapping<INPUT, OUTPUT> {
    private final List<FieldMapping<OUTPUT, INPUT>> fieldMappings;
    private final Class<OUTPUT> outputClass;

    public ObjectMapping(Class<OUTPUT> outputClass, Map<String, String> fieldMappings) {
        this.outputClass = outputClass;
        // Convert Map<String, String> to List<FieldMapping<OUTPUT, INPUT>>
        this.fieldMappings = new ArrayList<>();
        for (Map.Entry<String, String> entry : fieldMappings.entrySet()) {
            this.fieldMappings.add(new FieldMapping<>(entry.getKey(), entry.getValue()));
        }
    }

    public OUTPUT map(INPUT input) {
      return mergeInto(input, null);
    }

    public OUTPUT mergeInto(INPUT input, OUTPUT output) {
        try {
            if (output == null) {
              // Create new instance of output class
              output = outputClass.getDeclaredConstructor().newInstance();
            }
            
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
