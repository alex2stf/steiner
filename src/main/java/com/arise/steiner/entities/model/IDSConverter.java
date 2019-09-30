package com.arise.steiner.entities.model;

import com.arise.steiner.entities.Item;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.AttributeConverter;

/**
 * Class used to store multiple ids into CSV format
 *
 * @see Item#childIds
 */
public class IDSConverter implements AttributeConverter<Set<Long>, String> {


    public static String toCSV(Set<Long> values) {
        if (values == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        int counter = 0;
        for (Long s : values) {
            if (counter > 0) {
                sb.append(",");
            }
            sb.append(s);
            counter++;
        }
        return sb.toString();
    }

    public static Set<Long> fromCSV(String input) {
        if (input == null || input.trim().isEmpty()) {
            return new HashSet<>();
        }
        Set<Long> response = new HashSet<>();
        for (String s : input.split(",")) {
            if (!s.trim().isEmpty()) {
                Long value;
                try {
                    value = Long.valueOf(s);
                    response.add(value);
                } catch (NumberFormatException ex) {
                    return Collections.emptySet();
                }

            }
        }
        return response;
    }

    @Override
    public String convertToDatabaseColumn(Set<Long> longs) {
        return toCSV(longs);
    }

    @Override
    public Set<Long> convertToEntityAttribute(String s) {
        return fromCSV(s);
    }
}
