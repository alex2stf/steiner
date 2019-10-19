package com.arise.steiner.cqrs.commands;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

public class CreateWordCmd {

    @TargetAggregateIdentifier
    private final String id;

    public CreateWordCmd(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
