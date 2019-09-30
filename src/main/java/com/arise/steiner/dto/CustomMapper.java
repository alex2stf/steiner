package com.arise.steiner.dto;

import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CustomMapper {

    private final Logger log = LoggerFactory.getLogger(CustomMapper.class);

    private final Map<String, Object> input;

    public CustomMapper(Map<String, Object> input) {
        this.input = input;
    }


    public CustomMapper forLong(String name, KeyFoundCallback<Long> callback) {
        Long value = getLong(input, name);
        if (value != null) {
            callback.found(value);
        }
        return this;
    }


    public CustomMapper forString(String name, KeyFoundCallback<String> callback) {
        String value = getString(input, name);
        if (value != null) {
            callback.found(value);
        }
        return this;
    }


    public static String getStringStrict(Map<String, Object> input, String key) {
        if (input != null && !input.isEmpty() && input.containsKey(key) && input.get(key) instanceof String) {
            return (String) input.get(key);
        }
        return null;
    }


    public static String getString(Map<String, Object> input, String key) {
        if (input != null && !input.isEmpty() && input.containsKey(key)) {
            return String.valueOf(input.get(key));
        }
        return null;
    }

    public static Long getLong(Map<String, Object> input, String key) {
        Long value = null;
        if (input != null && !input.isEmpty() && input.containsKey(key)) {
            Object x = input.get(key);
            try {
                value = Long.valueOf(String.valueOf(x));
            } catch (Exception ex) {
                value = null;
            }
        }
        return value;
    }

    public interface KeyFoundCallback<T> {

        void found(T value);
    }
}
