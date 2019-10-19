package com.arise.steiner.services;

import com.arise.steiner.dto.TransitionRequest;
import com.arise.steiner.entities.Transition;

public interface TransitionService {
    void create(TransitionRequest request);
    Transition getByName(String id);
}
