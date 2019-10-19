package com.arise.steiner.cqrs.events;

public class WordCreatedEvent {
    private final String id;

    public WordCreatedEvent(String id){
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
