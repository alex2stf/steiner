package com.arise.steiner.cqrs.commands;

import com.arise.steiner.entities.Node;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

public class PatchNodeCmd {
    private final Node node;

    @TargetAggregateIdentifier
    private final String id;

    public PatchNodeCmd(Node node) {
        this.node = node;
        this.id = node.getId();
    }

    public Node getNode() {
        return node;
    }

    public String getId() {
        return id;
    }
}
