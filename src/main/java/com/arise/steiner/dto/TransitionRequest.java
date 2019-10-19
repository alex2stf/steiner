package com.arise.steiner.dto;

import java.util.List;
import java.util.Set;

public class TransitionRequest {

    private String name;

    private Set<String> commands;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<String> getCommands() {
        return commands;
    }

    public void setCommands(Set<String> commands) {
        this.commands = commands;
    }
}
