package com.arise.steiner.services;

import com.arise.steiner.cqrs.commands.CreateNodeCmd;
import com.arise.steiner.cqrs.commands.CreateWordCmd;
import com.arise.steiner.cqrs.commands.PatchNodeCmd;
import com.arise.steiner.cqrs.events.NodeCreateEvent;
import com.arise.steiner.cqrs.events.NodeUpdatedEvent;
import com.arise.steiner.cqrs.events.WordCreatedEvent;

public interface EventSourceService {




    void send(CreateNodeCmd cmd);

    void send(PatchNodeCmd patchNodeCmd);

    void send(CreateWordCmd createWordCmd);
}
