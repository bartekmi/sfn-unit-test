package com.sfnut.sfn;

import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FieldMapping<OUTPUT, INPUT> {
    private final String outputProperty;
    private final String propertyPath;
    private final List<PathSegment> pathSegments;
    private Method cachedSetter = null; // Lazy-loaded and cached

    public FieldMapping(String outputProperty, String propertyPath) {
        this.outputProperty = outputProperty;
        this.propertyPath = propertyPath;
        this.pathSegments = parsePropertyPath(propertyPath);
    }

    // Apply this field mapping to map a value from input to output
    public void apply(OUTPUT output, INPUT input) {
        try {
            Object value = extractValue(input);
            if (value != null) {
                // Lazy resolution of setter method
                if (cachedSetter == null) {
                    cachedSetter = findSetter(output.getClass(), outputProperty);
                }
                cachedSetter.invoke(output, value);
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to apply field mapping from '" + propertyPath + "' to '" + outputProperty + "'", e);
        }
    }

    // Extract value by following the property path
    private Object extractValue(INPUT input) {
        try {
            Object current = input;
            
            for (PathSegment segment : pathSegments) {
                if (current == null) {
                    return null; // Short-circuit on null
                }
                
                current = segment.getValue(current);
            }
            
            return current;
        } catch (Exception e) {
            throw new RuntimeException("Failed to extract value using path: " + propertyPath, e);
        }
    }

    // Parse property path into segments
    private List<PathSegment> parsePropertyPath(String path) {
        List<PathSegment> segments = new ArrayList<>();
        Pattern pattern = Pattern.compile("([^.\\[]+)(\\[(\\d+)\\])?");
        String[] parts = path.split("\\.");
        
        for (String part : parts) {
            Matcher matcher = pattern.matcher(part);
            if (matcher.matches()) {
                String propertyName = matcher.group(1);
                String indexStr = matcher.group(3);
                
                if (indexStr != null) {
                    int index = Integer.parseInt(indexStr);
                    segments.add(new IndexedPathSegment(propertyName, index));
                } else {
                    segments.add(new SimplePathSegment(propertyName));
                }
            } else {
                throw new IllegalArgumentException("Invalid property path segment: " + part);
            }
        }
        
        return segments;
    }

    // Find setter method for a property
    private Method findSetter(Class<?> clazz, String propertyName) throws NoSuchMethodException {
        String setterName = "set" + Character.toUpperCase(propertyName.charAt(0)) + propertyName.substring(1);
        
        // Try to find setter with any single parameter
        for (Method method : clazz.getMethods()) {
            if (method.getName().equals(setterName) && method.getParameterCount() == 1) {
                return method;
            }
        }
        
        throw new NoSuchMethodException("No setter found for property: " + propertyName + " in class: " + clazz.getName());
    }

    // Getters for code generation support
    public String getOutputProperty() {
        return outputProperty;
    }

    public String getPropertyPath() {
        return propertyPath;
    }

    public List<String> getPathSegmentNames() {
        List<String> names = new ArrayList<>();
        for (PathSegment segment : pathSegments) {
            names.add(segment.getPropertyName());
        }
        return names;
    }

    public List<PathSegment> getPathSegments() {
        return new ArrayList<>(pathSegments);
    }

    // Abstract base class for path segments
    public abstract static class PathSegment {
        protected final String propertyName;

        public PathSegment(String propertyName) {
            this.propertyName = propertyName;
        }

        public String getPropertyName() {
            return propertyName;
        }

        public abstract Object getValue(Object target) throws Exception;
        public abstract boolean isIndexed();
        public abstract Integer getIndex();
    }

    // Simple property access (e.g., "name")
    public static class SimplePathSegment extends PathSegment {
        public SimplePathSegment(String propertyName) {
            super(propertyName);
        }

        @Override
        public Object getValue(Object target) throws Exception {
            Method getter = findGetter(target.getClass(), propertyName);
            return getter.invoke(target);
        }

        @Override
        public boolean isIndexed() {
            return false;
        }

        @Override
        public Integer getIndex() {
            return null;
        }

        private Method findGetter(Class<?> clazz, String propertyName) throws NoSuchMethodException {
            String getterName = "get" + Character.toUpperCase(propertyName.charAt(0)) + propertyName.substring(1);
            try {
                return clazz.getMethod(getterName);
            } catch (NoSuchMethodException e) {
                String isGetterName = "is" + Character.toUpperCase(propertyName.charAt(0)) + propertyName.substring(1);
                return clazz.getMethod(isGetterName);
            }
        }
    }

    // Indexed property access (e.g., "items[0]")
    public static class IndexedPathSegment extends PathSegment {
        private final int index;

        public IndexedPathSegment(String propertyName, int index) {
            super(propertyName);
            this.index = index;
        }

        @Override
        public Object getValue(Object target) throws Exception {
            // First get the collection/array
            Method getter = findGetter(target.getClass(), propertyName);
            Object collection = getter.invoke(target);
            
            if (collection == null) {
                return null;
            }
            
            // Handle arrays
            if (collection.getClass().isArray()) {
                if (index >= Array.getLength(collection)) {
                    return null; // Index out of bounds
                }
                return Array.get(collection, index);
            }
            
            // Handle Lists
            if (collection instanceof List) {
                List<?> list = (List<?>) collection;
                if (index >= list.size()) {
                    return null; // Index out of bounds
                }
                return list.get(index);
            }
            
            throw new IllegalArgumentException("Property " + propertyName + " is not an array or List, cannot use index access");
        }

        @Override
        public boolean isIndexed() {
            return true;
        }

        @Override
        public Integer getIndex() {
            return index;
        }

        private Method findGetter(Class<?> clazz, String propertyName) throws NoSuchMethodException {
            String getterName = "get" + Character.toUpperCase(propertyName.charAt(0)) + propertyName.substring(1);
            try {
                return clazz.getMethod(getterName);
            } catch (NoSuchMethodException e) {
                String isGetterName = "is" + Character.toUpperCase(propertyName.charAt(0)) + propertyName.substring(1);
                return clazz.getMethod(isGetterName);
            }
        }
    }
}
