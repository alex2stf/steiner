package com.arise.steiner.cqrs.aggregates;

import com.arise.steiner.cqrs.commands.CreateNodeCmd;
import com.arise.steiner.cqrs.commands.CreateWordCmd;
import com.arise.steiner.cqrs.commands.PatchNodeCmd;
import com.arise.steiner.cqrs.events.NodeCreateEvent;
import com.arise.steiner.cqrs.events.NodeUpdatedEvent;
import com.arise.steiner.cqrs.events.WordCreatedEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;

import static com.arise.steiner.Mapper.fromCommand;
import static org.axonframework.modelling.command.AggregateLifecycle.apply;

/**
 * comenzile se pun doar in constructor
 * doar agregatul are voie sa faca apply
 */
@Aggregate
public class NodeAggregate {


    @AggregateIdentifier
    private String id;

    private String description;

    // no-arg constructor for Axon
    NodeAggregate() {
    }


    @CommandHandler
    public NodeAggregate(CreateNodeCmd cmd) {
        apply(fromCommand(cmd));
    }


    @CommandHandler
    public NodeAggregate(PatchNodeCmd cmd) {
        NodeUpdatedEvent event = new NodeUpdatedEvent();
        event.setId(cmd.getNode().getId());
        event.setSource(cmd.getNode().getSource());
        event.setDescription(cmd.getNode().getDescription());

        apply(event);
    }


    @CommandHandler
    public void handle(PatchNodeCmd patchNodeCmd){
        NodeUpdatedEvent event = new NodeUpdatedEvent();
        event.setId(patchNodeCmd.getNode().getId());
        event.setSource(patchNodeCmd.getNode().getSource());
        event.setDescription(patchNodeCmd.getNode().getDescription());

        apply(event);
    }


//    @CommandHandler
//    public void NodeAggregate(CreateWordCmd cmd){
//        apply(new WordCreatedEvent(cmd.getId()));
//    }

//    @EventHandler
    @EventSourcingHandler
    public void on(NodeCreateEvent event) {
        this.id = event.getId();
        this.description = event.getDescription();

        System.out.println("NODECREATED " + event);
        //TODO store in db???
    }

//    @EventHandler
@EventSourcingHandler
    public void on(NodeUpdatedEvent event){
        this.id = event.getId();
        this.description = event.getDescription();
        //TODO store in db???
        System.out.println("NodeUpdatedEvent " + event);
    }


//    @EventHandler
//    public void on(WordCreatedEvent event){
//        this.id = event.getId();
//        //TODO store in db???
//        System.out.println("NodeUpdatedEvent " + event);
//    }


}
