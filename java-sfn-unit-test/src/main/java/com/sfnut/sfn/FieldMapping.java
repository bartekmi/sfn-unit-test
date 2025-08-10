package com.sfnut.sfn;

import java.util.function.BiConsumer;
import java.util.function.Function;

public class FieldMapping<OUTPUT, INPUT> {
    private final BiConsumer<OUTPUT, Object> setter;
    private final Function<INPUT, Object> valueExtractor;

    // Constructor for simple field mapping: Output::field <- Input::field
    public <T> FieldMapping(BiConsumer<OUTPUT, T> setter, Function<INPUT, T> getter) {
        this.setter = (output, value) -> setter.accept(output, (T) value);
        this.valueExtractor = input -> getter.apply(input);
    }

    // Constructor for nested field mapping: Output::field <- Input::nested.field
    public <T, NESTED> FieldMapping(BiConsumer<OUTPUT, T> setter, Function<INPUT, NESTED> nestedGetter, Function<NESTED, T> fieldGetter) {
        this.setter = (output, value) -> {
            if (value != null) {
                setter.accept(output, (T) value);
            }
        };
        this.valueExtractor = input -> {
            NESTED nested = nestedGetter.apply(input);
            return nested != null ? fieldGetter.apply(nested) : null;
        };
    }

    // Apply this field mapping to map a value from input to output
    public void apply(OUTPUT output, INPUT input) {
        Object value = valueExtractor.apply(input);
        setter.accept(output, value);
    }
}
