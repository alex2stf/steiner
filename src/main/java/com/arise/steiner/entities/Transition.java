package com.arise.steiner.entities;

import com.arise.steiner.entities.model.StringIDSConverter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "st_transitions")
public class Transition implements Serializable {

    @Id
    @Column(name = "t_name")
    private String name;

    @Convert(converter = StringIDSConverter.class)
    @Column(name = "t_columns")
    private Set<String> commands;

    public Set<String> getCommands() {
        return commands;
    }

    public void setCommands(Set<String> commands) {
        this.commands = commands;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
