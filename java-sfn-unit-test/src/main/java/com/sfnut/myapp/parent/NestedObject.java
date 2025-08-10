package com.sfnut.myapp.parent;

import com.fasterxml.jackson.annotation.JsonProperty;

public class NestedObject {
    @JsonProperty("NestedField")
    private String nestedField;

    public NestedObject(String nestedField) {
      this.nestedField = nestedField;
    }

    // Getters and setters
    public String getNestedField() { return nestedField; }
    public void setNestedField(String nestedField) { this.nestedField = nestedField; }
}
