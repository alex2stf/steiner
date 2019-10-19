package com.arise.steiner.cqrs.events;

public class NodeCreateEvent {
    protected String id;
    private String source;
    private String description;

    public NodeCreateEvent setId(String id){
        this.id = "(" + id + ")";
        return this;
    }

    public String getId() {
        return id;
    }

    public String getSource() {
        return source;
    }

    public NodeCreateEvent setSource(String source) {
        this.source = source;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


}
