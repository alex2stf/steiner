package com.arise.steiner.services;

import com.arise.steiner.entities.Node;
import com.arise.steiner.dto.CreateNodeRequest;
import com.arise.steiner.errors.EntityNotFoundException;
import java.util.List;
import java.util.Map;

/**
 * Service Interface for managing Node.
 */
public interface NodesService {

    Node createNode(CreateNodeRequest createNodeRequest, String user, String userDomain);

    Node findOne(String id) throws EntityNotFoundException;

    List<Node> getByProductId(Long productId);

    void delete(String id);

    void save(Node node);

    List<Node> getMultiples(List<Long> documentsIds);

    void update(Node node, Map<String, Object> requestMap);
}
