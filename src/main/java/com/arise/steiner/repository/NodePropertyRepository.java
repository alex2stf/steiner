package com.arise.steiner.repository;

import com.arise.steiner.entities.Property;
import com.arise.steiner.entities.NodeProperty;
import com.arise.steiner.entities.model.InfoID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@SuppressWarnings("unused")
@Repository
public interface NodePropertyRepository extends JpaRepository<NodeProperty, InfoID> {

    NodeProperty findByPropertyAndNodeId(Property property, Long documentId);
}
