package com.arise.steiner.cqrs.commands;

import com.arise.steiner.entities.Node;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

public class CreateNodeCmd {


    @TargetAggregateIdentifier
    private final String id;
    private final Node node;


    public CreateNodeCmd(Node node){
        this.id = node.getId();
        this.node = node;
    }


    public String getId() {
        return id;
    }

    public Node getNode() {
        return node;
    }
}
