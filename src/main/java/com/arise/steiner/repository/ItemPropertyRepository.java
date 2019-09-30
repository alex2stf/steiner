package com.arise.steiner.repository;

import com.arise.steiner.entities.Property;
import com.arise.steiner.entities.ItemProperty;
import com.arise.steiner.entities.model.InfoID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@SuppressWarnings("unused")
@Repository
public interface ItemPropertyRepository extends JpaRepository<ItemProperty, InfoID> {

    ItemProperty findByPropertyAndItemId(Property property, Long fileId);
}
