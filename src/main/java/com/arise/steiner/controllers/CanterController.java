package com.arise.steiner.controllers;


import com.arise.steiner.client.EventsResponse;
import com.arise.steiner.config.Author;
import com.arise.steiner.dto.User;
import com.arise.steiner.services.CanterService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class CanterController {

    final CanterService canterService;

    public CanterController(CanterService canterService) {
        this.canterService = canterService;
    }

    public EventsResponse listEvents(@Author User owner){
            return canterService.listEvents(owner);
    }
}
