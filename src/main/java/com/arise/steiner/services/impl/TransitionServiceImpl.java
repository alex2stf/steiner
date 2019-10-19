package com.arise.steiner.services.impl;

import com.arise.steiner.dto.TransitionRequest;
import com.arise.steiner.entities.Transition;
import com.arise.steiner.repository.TransitionRepository;
import com.arise.steiner.services.TransitionService;
import org.springframework.stereotype.Service;

@Service
public class TransitionServiceImpl implements TransitionService {
    private final TransitionRepository transitionRepository;

    public TransitionServiceImpl(TransitionRepository transitionRepository) {
        this.transitionRepository = transitionRepository;
    }

    @Override
    public void create(TransitionRequest request) {
        Transition transition = new Transition();
        transition.setName(request.getName());
        transition.setCommands(request.getCommands());
        transitionRepository.save(transition);
    }

    @Override
    public Transition getByName(String id) {
        return transitionRepository.getOne(id);
    }
}
