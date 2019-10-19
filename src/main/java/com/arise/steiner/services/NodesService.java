package com.arise.steiner.services;

import com.arise.steiner.client.UpdateNodeRequest;
import com.arise.steiner.dto.User;
import com.arise.steiner.entities.Node;
import com.arise.steiner.client.CreateNodeRequest;
import com.arise.steiner.errors.EntityNotFoundException;
import scala.collection.script.Update;

import java.util.List;
import java.util.Map;

/**
 * Service Interface for managing Node.
 */
public interface NodesService {

    Node createNode(CreateNodeRequest createNodeRequest, User requestor);

    Node findOne(String id) throws EntityNotFoundException;

    List<Node> getByProductId(Long productId);

    void delete(String id);

    void save(Node node);

    List<Node> getMultiples(List<Long> documentsIds);

//    void update(Node node, Map<String, Object> requestMap);
    void update(Node node, UpdateNodeRequest request);
}
