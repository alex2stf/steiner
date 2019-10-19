package com.arise.steiner.cqrs.aggregates;

import akka.actor.AbstractActor;
import akka.actor.Props;
import akka.japi.pf.FI;
import com.arise.steiner.cqrs.commands.CreateNodeCmd;

public class NodeActor extends AbstractActor {
    @Override
    public Receive createReceive() {
        return receiveBuilder().match(CreateNodeCmd.class, new FI.UnitApply<CreateNodeCmd>() {
            @Override
            public void apply(CreateNodeCmd createNodeCmd) throws Exception {
                System.out.println("ACTOR RECEIVED createnodecommand " + createNodeCmd);
            }
        }).build();
    }

    public static Props props() {
        return Props.create(NodeActor.class);
    }
}
