package com.arise.steiner.entities.model;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.AttributeConverter;
import org.springframework.util.StringUtils;

public class CSVDataStr implements AttributeConverter<Set<String>, String> {

    protected Set<String> values;

    public static <T> String toCSV(Set<T> values) {
        if (values == null || values.isEmpty()) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        int counter = 0;
        for (Object s : values) {
            if (counter > 0) {
                sb.append(",");
            }
            sb.append(String.valueOf(s));
            counter++;
        }
        return sb.toString();
    }

    public static <T> Set<T> fromCSV(String input, Handler<T> handler) {
        if (input == null || input.trim().isEmpty()) {
            return new HashSet<>();
        }
        Set<T> response = new HashSet<>();
        for (String s : input.split(",")) {
            if (StringUtils.hasText(s)) {
                T data = handler.convertItem(s);
                response.add(data);
            }
        }
        return response;
    }



    public Set<String> getValues() {
        return values;
    }

    public void setValues(Set<String> values) {
        this.values = values;
    }


    @Override
    public String convertToDatabaseColumn(Set<String> strings) {
        return toCSV(strings);
    }

    @Override
    public Set<String> convertToEntityAttribute(String s) {
        return fromCSV(s, data -> data);
    }

    public interface Handler<D> {
        D convertItem(String data);
    }
}
