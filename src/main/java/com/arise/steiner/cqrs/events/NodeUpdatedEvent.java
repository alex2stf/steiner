package com.arise.steiner.cqrs.events;


public class NodeUpdatedEvent extends NodeCreateEvent {
    public NodeUpdatedEvent setId(String id){
        this.id = "u(" + id + ")";
        return this;
    }

}
