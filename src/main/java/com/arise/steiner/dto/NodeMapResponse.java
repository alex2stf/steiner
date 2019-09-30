package com.arise.steiner.dto;

import com.arise.steiner.entities.Node;
import java.util.Map;


public class NodeMapResponse {

    private Map<String, Node> map;

    public Map<String, Node> getMap() {
        return map;
    }

    public void setMap(Map<String, Node> map) {
        this.map = map;
    }
}
