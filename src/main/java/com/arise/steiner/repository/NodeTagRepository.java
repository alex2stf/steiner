package com.arise.steiner.repository;

import com.arise.steiner.entities.NodeTag;
import com.arise.steiner.entities.Word;
import com.arise.steiner.entities.model.InfoID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@SuppressWarnings("unused")
@Repository
public interface NodeTagRepository extends JpaRepository<NodeTag, InfoID> {

    NodeTag findByTagAndNodeId(Word tag, String nodeId);
}
