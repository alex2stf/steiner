package com.arise.steiner.controllers;

import com.arise.steiner.dto.TransitionRequest;
import com.arise.steiner.entities.Transition;
import com.arise.steiner.names.Routes;
import com.arise.steiner.services.TransitionService;
import org.springframework.web.bind.annotation.*;

@RestController
public class TransitionController {

    private final TransitionService transitionService;

    public TransitionController(TransitionService transitionService) {
        this.transitionService = transitionService;
    }

    @PostMapping(Routes.TRANSITIONS)
    public void createTransition(@RequestBody TransitionRequest request){
        transitionService.create(request);
    }

    @GetMapping(Routes.TRANSITIONS + "/{name}")
    public TransitionRequest getById(@PathVariable String name){
        TransitionRequest reponse = new TransitionRequest();
        Transition transition = transitionService.getByName(name);
        reponse.setName(transition.getName());
        reponse.setCommands(transition.getCommands());
        return reponse;
    }
}
