package com.sfnut.sfn;

import java.util.function.BiConsumer;
import java.util.function.Function;

public class FieldMapping<OUTPUT, INPUT> {
    private final BiConsumer<OUTPUT, Object> setter;
    private final Function<INPUT, Object> valueExtractor;

    // Constructor for arbitrary nested field mapping using varargs
    // Usage examples:
    // Simple: new FieldMapping<>(Output::setField, Input::getField)
    // Nested: new FieldMapping<>(Output::setField, Input::getNested, Nested::getField)
    // Multi-nested: new FieldMapping<>(Output::setField, Input::getA, A::getB, B::getC, C::getValue)
    @SafeVarargs
    public <T> FieldMapping(BiConsumer<OUTPUT, T> setter, Function<INPUT, ?> firstGetter, Function<?, ?>... nestedGetters) {
        this.setter = (output, value) -> {
            if (value != null) {
                @SuppressWarnings("unchecked")
                T typedValue = (T) value;
                setter.accept(output, typedValue);
            }
        };
        
        this.valueExtractor = input -> {
            // Apply the first getter
            Object current = firstGetter.apply(input);
            
            // Chain through the nested getters
            for (Function<?, ?> getter : nestedGetters) {
                if (current == null) {
                    return null; // Short-circuit on null
                }
                
                @SuppressWarnings("unchecked")
                Function<Object, Object> typedGetter = (Function<Object, Object>) getter;
                current = typedGetter.apply(current);
            }
            
            return current;
        };
    }

    // Apply this field mapping to map a value from input to output
    public void apply(OUTPUT output, INPUT input) {
        Object value = valueExtractor.apply(input);
        setter.accept(output, value);
    }
}
