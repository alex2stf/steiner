package com.arise.steiner.services.impl;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import com.arise.steiner.cqrs.aggregates.NodeActor;
import com.arise.steiner.cqrs.commands.CreateNodeCmd;
import com.arise.steiner.cqrs.commands.CreateWordCmd;
import com.arise.steiner.cqrs.commands.PatchNodeCmd;
import com.arise.steiner.cqrs.events.NodeCreateEvent;
import com.arise.steiner.cqrs.events.NodeUpdatedEvent;
import com.arise.steiner.cqrs.events.WordCreatedEvent;
import com.arise.steiner.services.EventSourceService;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

@Service
public class AxonEventSourceServiceImpl implements EventSourceService {

    @Autowired
    CommandGateway commandGateway;

    @Autowired
    QueryGateway queryGateway;


    final ActorSystem actorSystem = ActorSystem.create();
    ActorRef nodeActor;
    public AxonEventSourceServiceImpl(){
        nodeActor = actorSystem.actorOf(NodeActor.props());
    }




    @Override
    public void send(CreateNodeCmd cmd) {
        sendObject(cmd);
    }

    @Override
    public void send(PatchNodeCmd cmd) {
        commandGateway.send(cmd);
        nodeActor.tell(cmd, ActorRef.noSender());
    }

    @Override
    public void send(CreateWordCmd cmd) {
//        sendObject(cmd);
    }


    private void sendObject(Object c){
        commandGateway.send(c);
        nodeActor.tell(c, ActorRef.noSender());
    }
}
