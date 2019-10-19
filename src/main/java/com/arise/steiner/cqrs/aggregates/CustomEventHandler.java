package com.arise.steiner.cqrs.aggregates;

import com.arise.steiner.cqrs.commands.CreateNodeCmd;
import com.arise.steiner.cqrs.commands.PatchNodeCmd;
import com.arise.steiner.cqrs.events.NodeCreateEvent;
import com.arise.steiner.cqrs.events.NodeUpdatedEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;

import java.util.EventListener;

import static com.arise.steiner.Mapper.fromCommand;
import static org.axonframework.modelling.command.AggregateLifecycle.apply;

/**
 * comenzile se pun doar in constructor
 * doar agregatul are voie sa faca apply
 */
public class CustomEventHandler implements EventListener{



    @EventHandler
//    @EventSourcingHandler
    public void on(NodeCreateEvent event) {

        System.out.println("NODECREATED  CUSTOM HANDLER" + event);
        //TODO store in db???
    }


//    @EventHandler
//    public void on(WordCreatedEvent event){
//        this.id = event.getId();
//        //TODO store in db???
//        System.out.println("NodeUpdatedEvent " + event);
//    }


}
