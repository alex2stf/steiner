package com.arise.steiner.entities.model;

import java.util.Set;
import javax.persistence.AttributeConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CSVDataLong implements AttributeConverter<Set<Long>, String> {
    private final Logger log = LoggerFactory.getLogger(CSVDataLong.class);

    @Override
    public String convertToDatabaseColumn(Set<Long> integers) {
        return CSVDataStr.toCSV(integers);
    }

    @Override
    public Set<Long> convertToEntityAttribute(String input) {
        return CSVDataStr.fromCSV(input, data -> {
            Long c = null;
            try {
                c = Long.valueOf(data);
            } catch (NumberFormatException ex){
                log.error("Failed to convert " + data + " to long");
            }
            return c;
        });
    }
}
