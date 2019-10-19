package com.arise.steiner.client;

public class EventDTO {
    private String name;
    private String[] visibility;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String[] getVisibility() {
        return visibility;
    }

    public void setVisibility(String[] visibility) {
        this.visibility = visibility;
    }
}
