package com.arise.steiner.services;

import com.arise.canter.Event;
import com.arise.canter.Registry;
import com.arise.steiner.client.EventDTO;
import com.arise.steiner.client.EventsResponse;
import com.arise.steiner.dto.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class CanterService {
    final Registry cmdRegistry;

    public CanterService(Registry cmdRegistry) {
        this.cmdRegistry = cmdRegistry;
    }

    public EventsResponse listEvents(User owner){
        EventsResponse response = new EventsResponse();
        for (Event event: cmdRegistry.getEvents()){
            EventDTO eventDTO = new EventDTO();
            eventDTO.setName(event.getName());

        }
        return response;
    }
}
