package com.arise.steiner.entities.model;

import java.util.Set;
import javax.persistence.AttributeConverter;

public class StringIDSConverter implements AttributeConverter<Set<String>, String> {


  @Override
  public String convertToDatabaseColumn(Set<String> strings) {
    return CSVDataStr.toCSV(strings);
  }

  @Override
  public Set<String> convertToEntityAttribute(String s) {
    return CSVDataStr.fromCSV(s, data -> data);
  }
}
