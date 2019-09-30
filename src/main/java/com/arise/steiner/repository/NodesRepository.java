package com.arise.steiner.repository;

import com.arise.steiner.entities.Node;
import java.util.List;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data JPA repository for the Node entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NodesRepository extends JpaRepository<Node, String> {

    List<Node> getByProductId(Long productId);

    List<Node> findByIdIn(List<Long> ids);

    Set<Node> findByProductIdAndStatus(Long productId, String status);
    Set<Node> findByProductIdAndStatusAndPhase(Long productId, String status, String phase);


}
