package com.arise.steiner.dto;

import com.arise.steiner.entities.Node;
import java.util.List;

public class NodeListDTO {

    private List<Node> nodes;

    public static NodeListDTO from(List<Node> nodes) {
        NodeListDTO nodeListDTO = new NodeListDTO();
        nodeListDTO.setNodes(nodes);
        return nodeListDTO;
    }

    public List<Node> getNodes() {
        return nodes;
    }

    public void setNodes(List<Node> nodes) {
        this.nodes = nodes;
    }
}
