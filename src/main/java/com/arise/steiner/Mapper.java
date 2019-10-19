package com.arise.steiner;

import com.arise.steiner.client.CreateNodeRequest;
import com.arise.steiner.cqrs.commands.CreateNodeCmd;
import com.arise.steiner.cqrs.commands.PatchNodeCmd;
import com.arise.steiner.cqrs.events.NodeCreateEvent;
import com.arise.steiner.cqrs.events.NodeUpdatedEvent;
import com.arise.steiner.entities.Node;

import java.util.Date;

public class Mapper {

    public static Node fromRequest(CreateNodeRequest request){
        Node node = new Node();
        node.setDescription(request.getDescription());
        node.setType(request.getType());
        node.setReason(request.getReason());
        node.setProductId(request.getProductId());
        node.setPhase(request.getPhase());
        node.setSource(request.getSource());
        node.setStatus(request.getStatus());
        node.setProduct(request.getProduct());
        node.setCode(request.getCode());
        node.setCreationDate(new Date());
        return node;
    }


    public static NodeCreateEvent fromCommand(CreateNodeCmd cmd){
        NodeCreateEvent event = new NodeCreateEvent();
        event.setId(cmd.getId());
        event.setSource(cmd.getNode().getSource());
        event.setDescription(cmd.getNode().getDescription());
        return event;
    }


    public static NodeUpdatedEvent fromCommand(PatchNodeCmd cmd){
        NodeUpdatedEvent event = updateNodeEvent(cmd.getNode());
        return event;
    }

    public static NodeCreateEvent createdNodeEvent(Node node){
        NodeCreateEvent event = new NodeCreateEvent();
        event.setId(node.getId());
        event.setSource(node.getSource());
        event.setDescription(node.getDescription());
        return event;
    }

    public static NodeUpdatedEvent updateNodeEvent(Node node){
        NodeUpdatedEvent event = new NodeUpdatedEvent();
        event.setId(node.getId());
        event.setSource(node.getSource());
        event.setDescription(node.getDescription());
        return event;
    }
}
