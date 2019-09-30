package com.arise.steiner.repository;

import com.arise.steiner.entities.Property;
import com.arise.steiner.entities.model.InfoID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PropertyRepository extends JpaRepository<Property, InfoID> {

    Property findByKeyAndValue(String key, String value);
}
